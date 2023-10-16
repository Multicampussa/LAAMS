package multicampussa.laams.home.member.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.domain.Director;
import multicampussa.laams.home.member.dto.MemberSignUpDto;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.home.member.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
//    private final JavaMailSender javaMailSender;
    private final Random random = new SecureRandom();

    // 회원가입
    public ResponseEntity<String> signUp(MemberSignUpDto memberSignUpDto) {
        if (memberRepository.existsByEmail(memberSignUpDto.getEmail()) && !memberRepository.findByEmail(memberSignUpDto.getEmail()).get().getIsDelete()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 이메일입니다.");
        } else {
            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(memberSignUpDto.getPassword());

            Director director;
            if (!memberRepository.existsByEmail(memberSignUpDto.getEmail()) || !memberRepository.findByEmail(memberSignUpDto.getEmail()).get().getIsVerified()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 인증을 진행해주세요.");
            } else {
                // 삭제한 이메일로 다시 한 번 회원가입 할 때
                director = memberRepository.findByEmail(memberSignUpDto.getEmail()).get();

                director.update(memberSignUpDto, encodedPassword);

                Director savedMember = memberRepository.save(director);
            }

            return ResponseEntity.status(HttpStatus.OK).body("회원 가입에 성공하였습니다.");
        }
    }
}
