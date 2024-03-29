package multicampussa.laams.home.member.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.config.RedisUtil;
import multicampussa.laams.home.member.domain.Member;
import multicampussa.laams.home.member.dto.*;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.home.member.repository.MemberRepository;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;
    private final ExamExamineeRepository examExamineeRepository;
    private final Random random = new SecureRandom();

    // 회원가입
    public ResponseEntity<String> signUp(MemberSignUpDto memberSignUpDto) {
        List<Member> members = memberRepository.findAll();

        if ((memberRepository.existsByEmail(memberSignUpDto.getEmail()) && !memberRepository.findByEmail(memberSignUpDto.getEmail()).get().getIsDelete())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 이메일입니다.");
        }

        if (memberRepository.existsById(memberSignUpDto.getId())
                && !memberRepository.findById(memberSignUpDto.getId()).get().getIsDelete()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberSignUpDto.getPw());
        Member newMember = redisUtil.get(memberSignUpDto.getEmail(), Member.class);
        if (newMember == null || !newMember.getIsVerified()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 인증을 진행해주세요.");
        } else {
            // 삭제한 이메일로 다시 한 번 회원가입 할 때
            if (memberRepository.existsByEmail(memberSignUpDto.getEmail())) {
                Member oldMember = memberRepository.findByEmail(memberSignUpDto.getEmail()).get();
                oldMember.update(memberSignUpDto, encodedPassword);
                memberRepository.save(oldMember);
            } else {
                newMember.update(memberSignUpDto, encodedPassword);
                memberRepository.save(newMember);
            }

            redisUtil.delete(newMember.getEmail());
        }

        return ResponseEntity.status(HttpStatus.OK).body("회원 가입에 성공하였습니다.");
    }

    // 이메일 인증
    public void requestEmailVerification(String email) {
        String code = generateVerificationCode();

        // 이메일이 공백인 경우 오류 발생
        if (email == "") {
            throw new IllegalArgumentException("400: 이메일을 입력해주세요.");
        }

        if ((memberRepository.existsByEmail(email) && !memberRepository.findByEmail(email).get().getIsDelete())) {
            throw new IllegalArgumentException("409: 이미 존재하는 이메일입니다.");
        }

        Member member = new Member();
        member.updateVerificationCode(email, code);
        redisUtil.set(email, member, 10);

        try {
            String message = "다음 코드를 입력하여 이메일을 확인해주세요: " + code;
            sendEmail(email, "이메일 확인 코드", message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("400: 이메일 형식이 잘못되었습니다.");
        }
    }

    // 인증코드 발급
    private String generateVerificationCode() {
        int code = 100000 + random.nextInt(900000); // 100000에서 999999 사이의 난수 생성
        return Integer.toString(code);
    }

    // 이메일 보내기 (JavaMailSender 사용)
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

    // 이메일 인증코드 확인
    public void confirmEmailVerification(String email, String code) {
        Member member = redisUtil.get(email, Member.class);

        if (member == null) {
            throw new RuntimeException("인증 시간이 초과되었습니다.");
        }

        if (!member.getVerificationCode().equals(code)) {
            throw new RuntimeException("인증 코드가 일치하지 않습니다.");
        }

        member.updateVerified(true);
        redisUtil.setInfinity(email, member);
    }

    // 감독관 정보 불러오기
    public MemberDto DirectorInfo(String id) {
        if (memberRepository.existsById(id)) {
            Member member = memberRepository.findById(id).get();

            if (member.getIsDelete()) {
                throw new IllegalArgumentException("해당 아이디는 삭제되었습니다.");
            }

            return Member.toMemberDto(member);
        } else {
            return null;
        }
    }

    // 운영자 정보 불러오기
    public MemberDto ManagerInfo(String id) {
        return Member.toMemberDto(memberRepository.findById(id).get());
    }

    // 센터담당자 정보 불러오기
    public MemberDto CenterManagerInfo(String id) {
        return Member.toMemberDto(memberRepository.findById(id).get());
    }

    // 로그인 및 토큰 발급
    public ResponseEntity<Map<String, Object>> signIn(LoginRequestDto loginRequestDto, String refreshToken) {
        Map<String, Object> response = new HashMap<>();
        Optional<Member> memberOptional;

        if (memberRepository.existsById(loginRequestDto.getId())) {
            memberOptional = memberRepository.findById(loginRequestDto.getId());
            memberOptional.get().updateRefreshToken(refreshToken);
            memberRepository.save(memberOptional.get());

            if (!memberOptional.get().getIsDelete()) {
                Member member = memberOptional.get();
                if (passwordEncoder.matches(loginRequestDto.getPw(), member.getPw())) {
                    response.put("message", "로그인에 성공하였습니다.");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    response.put("message", "비밀번호가 일치하지 않습니다.");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
            } else {
                response.put("message", "탈퇴한 유저입니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } else {
            response.put("message", "사용자를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 운영자가 감독관 또는 운영자 또는 센터담당자의 정보를 조회하는 서비스
    public MemberDto UserInfo(String memberId) {
        if (memberRepository.existsById(memberId)) {
            return Member.toMemberDto(memberRepository.findById(memberId).get());
        } else {
            throw new IllegalArgumentException("해당 아이디는 존재하지 않습니다.");
        }
    }

    // 회원 정보를 수정하는 서비스
    public ResponseEntity<Map<String, Object>> updateMemberByUser(String id, String authority, MemberUpdateDto memberUpdateDto) {
        Map<String, Object> response = new HashMap<>();
        Member oldMember;
        Member member;
        Member centerManager;

        // DB에 없는 ID를 검색하려고 하면 IllegalArgumentException
        try {
            if (authority.equals("ROLE_DIRECTOR")) {
                if (id.equals(memberUpdateDto.getId())) {
                    oldMember = memberRepository.findById(id).get();

                    if (oldMember.getIsDelete()) {
                        response.put("message", "해당 유저는 삭제되었습니다.");
                        response.put("code", HttpStatus.NOT_FOUND.value());
                        return ResponseEntity.ok(response);
                    }

                    Member newMember = redisUtil.get(memberUpdateDto.getEmail(), multicampussa.laams.home.member.domain.Member.class);

                    if (!oldMember.getEmail().equals(memberUpdateDto.getEmail()) &&
                            (memberRepository.existsByEmail(memberUpdateDto.getEmail()) ||
                                    memberRepository.existsByEmail(memberUpdateDto.getEmail()) ||
                                    memberRepository.existsByEmail(memberUpdateDto.getEmail()))) {
                        response.put("message", "이미 존재하는 이메일입니다.");
                        response.put("status", HttpStatus.BAD_REQUEST.value());

                        return ResponseEntity.ok(response);
                    }

                    if (!oldMember.getEmail().equals(memberUpdateDto.getEmail()) && (newMember == null || !newMember.getIsVerified())) {
                        response.put("message", "이메일 인증을 진행해주세요.");
                        response.put("status", HttpStatus.BAD_REQUEST.value());

                        return ResponseEntity.ok(response);
                    }

                    oldMember.update(memberUpdateDto);
                    memberRepository.save(oldMember);
                    redisUtil.delete(oldMember.getEmail());

                    response.put("message", "회원 정보가 성공적으로 수정되었습니다.");
                    response.put("code", HttpStatus.OK.value());
                    response.put("status", "success");

                    return ResponseEntity.ok(response);
                } else {
                    response.put("message", "접근 권한이 없습니다.");
                    response.put("status", HttpStatus.UNAUTHORIZED.value());

                    return ResponseEntity.ok(response);
                }
            } else if (authority.equals("ROLE_MANAGER")) {
                if (memberRepository.existsById(memberUpdateDto.getId())) {
                    if (!memberUpdateDto.getId().equals(id)) {
                        response.put("message", "접근 권한이 없습니다.");
                        response.put("status", HttpStatus.UNAUTHORIZED.value());

                        return ResponseEntity.ok(response);
                    }

                    member = memberRepository.findById(id).get();

                    if (!member.getEmail().equals(memberUpdateDto.getEmail()) &&
                            (memberRepository.existsByEmail(memberUpdateDto.getEmail()) ||
                                    memberRepository.existsByEmail(memberUpdateDto.getEmail()) ||
                                    memberRepository.existsByEmail(memberUpdateDto.getEmail()))) {
                        response.put("message", "이미 존재하는 이메일입니다.");
                        response.put("status", HttpStatus.BAD_REQUEST.value());

                        return ResponseEntity.ok(response);
                    }

                    member.update(memberUpdateDto);
                    memberRepository.save(member);

                    response.put("message", "회원 정보가 성공적으로 수정되었습니다.");
                    response.put("code", HttpStatus.OK.value());
                    response.put("status", "success");

                    return ResponseEntity.ok(response);
                } else if (memberRepository.existsById(memberUpdateDto.getId())) {
                    oldMember = memberRepository.findById(memberUpdateDto.getId()).get();

                    if (oldMember.getIsDelete()) {
                        response.put("message", "해당 계정은 삭제되었습니다.");
                        response.put("status", HttpStatus.NOT_FOUND.value());

                        return ResponseEntity.ok(response);
                    }

                    if (!oldMember.getEmail().equals(memberUpdateDto.getEmail()) &&
                            (memberRepository.existsByEmail(memberUpdateDto.getEmail()) ||
                                    memberRepository.existsByEmail(memberUpdateDto.getEmail()) ||
                                    memberRepository.existsByEmail(memberUpdateDto.getEmail()))) {
                        response.put("message", "이미 존재하는 이메일입니다.");
                        response.put("status", HttpStatus.BAD_REQUEST.value());

                        return ResponseEntity.ok(response);
                    }

                    oldMember.update(memberUpdateDto);
                    memberRepository.save(oldMember);

                    response.put("message", "회원 정보가 성공적으로 수정되었습니다.");
                    response.put("code", HttpStatus.OK.value());
                    response.put("status", "success");

                    return ResponseEntity.ok(response);
                } else if (memberRepository.existsById(memberUpdateDto.getId())) {
                    response.put("message", "접근 권한이 없습니다.");
                    response.put("code", HttpStatus.UNAUTHORIZED.value());

                    return ResponseEntity.ok(response);
                } else {
                    response.put("message", "해당하는 계정이 없습니다.");
                    response.put("code", HttpStatus.NOT_FOUND.value());

                    return ResponseEntity.ok(response);
                }
            } else if (authority.equals("ROLE_CENTER_MANAGER")) {
                if (memberRepository.existsById(memberUpdateDto.getId())) {
                    response.put("message", "접근 권한이 없습니다.");
                    response.put("code", HttpStatus.UNAUTHORIZED.value());

                    return ResponseEntity.ok(response);
                } else if (memberRepository.existsById(memberUpdateDto.getId())) {
                    oldMember = memberRepository.findById(memberUpdateDto.getId()).get();

                    if (oldMember.getIsDelete()) {
                        response.put("message", "해당 계정은 삭제되었습니다.");
                        response.put("status", HttpStatus.NOT_FOUND.value());

                        return ResponseEntity.ok(response);
                    }

                    if (!oldMember.getEmail().equals(memberUpdateDto.getEmail()) &&
                            (memberRepository.existsByEmail(memberUpdateDto.getEmail()) ||
                                    memberRepository.existsByEmail(memberUpdateDto.getEmail()) ||
                                    memberRepository.existsByEmail(memberUpdateDto.getEmail()))) {
                        response.put("message", "이미 존재하는 이메일입니다.");
                        response.put("status", HttpStatus.BAD_REQUEST.value());

                        return ResponseEntity.ok(response);
                    }

                    oldMember.update(memberUpdateDto);
                    memberRepository.save(oldMember);

                    response.put("message", "회원 정보가 성공적으로 수정되었습니다.");
                    response.put("code", HttpStatus.OK.value());
                    response.put("status", "success");

                    return ResponseEntity.ok(response);
                } else if (memberRepository.existsById(memberUpdateDto.getId())) {
                    if (!memberUpdateDto.getId().equals(id)) {
                        response.put("message", "접근 권한이 없습니다.");
                        response.put("status", HttpStatus.UNAUTHORIZED.value());

                        return ResponseEntity.ok(response);
                    }

                    centerManager = memberRepository.findById(id).get();

                    if (!centerManager.getEmail().equals(memberUpdateDto.getEmail()) &&
                            (memberRepository.existsByEmail(memberUpdateDto.getEmail()) ||
                                    memberRepository.existsByEmail(memberUpdateDto.getEmail()) ||
                                    memberRepository.existsByEmail(memberUpdateDto.getEmail()))) {
                        response.put("message", "이미 존재하는 이메일입니다.");
                        response.put("status", HttpStatus.BAD_REQUEST.value());

                        return ResponseEntity.ok(response);
                    }

                    centerManager.update(memberUpdateDto);
                    memberRepository.save(centerManager);

                    response.put("message", "회원 정보가 성공적으로 수정되었습니다.");
                    response.put("code", HttpStatus.OK.value());
                    response.put("status", "success");

                    return ResponseEntity.ok(response);
                } else {
                    response.put("message", "해당하는 계정이 없습니다.");
                    response.put("code", HttpStatus.NOT_FOUND.value());

                    return ResponseEntity.ok(response);
                }
            } else {
                response.put("message", "authority를 다시 입력해주세요.");
                response.put("code", HttpStatus.BAD_REQUEST.value());

                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("message", memberUpdateDto.getId() + "은 존재하지 않습니다.");
            response.put("status", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // 비밀번호 변경
    public void changePassword(MemberUpdatePasswordDto requestDto) {
        if (memberRepository.existsById(requestDto.getId())) {
            Member member = memberRepository.findById(requestDto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

            if (member.getIsDelete()) {
                throw new IllegalArgumentException("해당 계정은 삭제되었습니다.");
            }

            // 기존 비밀번호 안맞으면 Exception
            if (!passwordEncoder.matches(requestDto.getOldPassword(), member.getPw())) {
                throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
            }

            member.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
            memberRepository.save(member);
        } else if (memberRepository.existsById(requestDto.getId())) {
            Member member = memberRepository.findById(requestDto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

            // 기존 비밀번호 안맞으면 Exception
            if (!passwordEncoder.matches(requestDto.getOldPassword(), member.getPw())) {
                throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
            }

            member.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
            memberRepository.save(member);
        } else if (memberRepository.existsById(requestDto.getId())) {
            Member member = memberRepository.findById(requestDto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

            // 기존 비밀번호 안맞으면 Exception
            if (!passwordEncoder.matches(requestDto.getOldPassword(), member.getPw())) {
                throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
            }

            member.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
            memberRepository.save(member);
        } else {
            throw new IllegalArgumentException("authority를 다시 입력해주세요.");
        }
    }

    // 회원 탈퇴
    public void deleteMember(String id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + "인 ID는 존재하지 않습니다."));

        if (member.getIsDelete()) {
            throw new IllegalArgumentException("해당 계정은 이미 삭제되었습니다.");
        }

        member.delete();
        memberRepository.save(member);
    }

    // 아이디 찾기
    public MemberInfoDto findId(String email, String memberName) {
        MemberInfoDto responseDto = new MemberInfoDto();
        Member member;

        if (memberRepository.existsByEmail(email)) {
            member = memberRepository.findByEmail(email).get();
            if (!member.getName().equals(memberName)) {
                throw new IllegalArgumentException("이름과 이메일이 일치하지 않습니다.");
            }
            return responseDto.fromEntityByDirector(member);
        } else if (memberRepository.existsByEmail(email)) {
            member = memberRepository.findByEmail(email).get();
            if (!member.getName().equals(memberName)) {
                throw new IllegalArgumentException("이름과 이메일이 일치하지 않습니다.");
            }
            return responseDto.fromEntityByManager(member);
        } else if (memberRepository.existsByEmail(email)) {
            member = memberRepository.findByEmail(email).get();
            if (!member.getName().equals(memberName)) {
                throw new IllegalArgumentException("이름과 이메일이 일치하지 않습니다.");
            }
            return responseDto.fromEntityByCenterManager(member);
        } else {
            throw new IllegalArgumentException("해당하는 이메일이 없습니다.");
        }
    }

    // 비밀번호 찾기
    public void findPassword(FindPasswordDto findPasswordDto) {
        String tempPassword = UUID.randomUUID().toString().split("-")[0];
        Member member;

        if (memberRepository.existsById(findPasswordDto.getId())) {
            member = memberRepository.findById(findPasswordDto.getId()).get();
            if (!member.getEmail().equals(findPasswordDto.getEmail())) {
                throw new IllegalArgumentException("아이디와 이메일이 일치하지 않습니다.");
            }

            member.updatePassword(passwordEncoder.encode(tempPassword));
            memberRepository.save(member);
        } else if (memberRepository.existsById(findPasswordDto.getId())) {
            member = memberRepository.findById(findPasswordDto.getId()).get();
            if (!member.getEmail().equals(findPasswordDto.getEmail())) {
                throw new IllegalArgumentException("아이디와 이메일이 일치하지 않습니다.");
            }

            member.updatePassword(passwordEncoder.encode(tempPassword));
            memberRepository.save(member);
        } else if (memberRepository.existsById(findPasswordDto.getId())) {
            member = memberRepository.findById(findPasswordDto.getId()).get();
            if (!member.getEmail().equals(findPasswordDto.getEmail())) {
                throw new IllegalArgumentException("아이디와 이메일이 일치하지 않습니다.");
            }

            member.updatePassword(passwordEncoder.encode(tempPassword));
            memberRepository.save(member);
        } else {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        }

        try {
            String message = "새로 등록된 임시 비밀번호로 로그인해주세요: " + tempPassword;
            sendEmail(findPasswordDto.getEmail(), "비밀번호 찾기", message);
        } catch (Exception e) {
            throw new IllegalArgumentException("이메일 형식이 잘못되었습니다.");
        }
    }

    // 운영자 회원가입을 위한 비밀번호 암호화
    public EncodedPasswordDto encodedPassword(EncodedPasswordDto encodedPasswordDto) {
        encodedPasswordDto.setPw(passwordEncoder.encode(encodedPasswordDto.getPw()));
        return encodedPasswordDto;
    }

    // 해당 아이디가 있는지 확인하는 로직
    public boolean isPresentId(String id) {
        return memberRepository.existsById(id);
    }

    // 응시자 정보가 일치하는지 확인하는 로직
    public void isPresentExamExaminee(ExamExamineeLoginRequestDto loginRequestDto) {
        ExamExaminee examExaminee = examExamineeRepository.findByExamineeCode(loginRequestDto.getExamineeCode())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수험번호입니다."));

        if (!examExaminee.getExaminee().getBirth().equals(loginRequestDto.getBirth())) {
            throw new IllegalArgumentException("수험자 정보가 일치하지 않습니다.");
        }
    }

    // 모든 감독관의 ID를 뽑아오는 로직
    public List<String> getDirectors() {
        List<String> directors = new ArrayList<>();
        for (Member member : memberRepository.findAll()) {
            directors.add(member.getId());
        }
        return directors;
    }

    // 로그인 및 토큰 발급
    public ResponseEntity<Map<String, Object>> signInForExamExaminee(ExamExamineeLoginRequestDto loginRequestDto, String refreshToken) {
        Map<String, Object> response = new HashMap<>();
        ExamExaminee examExaminee = examExamineeRepository.findByExamineeCode(loginRequestDto.getExamineeCode()).get();
        examExaminee.updateRefreshToken(refreshToken);
        examExamineeRepository.save(examExaminee);
        response.put("message", "로그인에 성공하였습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
