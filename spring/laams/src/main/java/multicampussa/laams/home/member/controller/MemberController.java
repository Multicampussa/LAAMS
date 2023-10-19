package multicampussa.laams.home.member.controller;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.member.dto.MemberDto;
import multicampussa.laams.home.member.dto.*;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.home.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody MemberSignUpDto memberSignUpDto) {
        ResponseEntity<String> result = memberService.signUp(memberSignUpDto);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", result.getBody());
        resultMap.put("status", result.getStatusCodeValue());
        return new ResponseEntity<Map<String, Object>>(resultMap, result.getStatusCode());
    }

    // 로그인 및 토큰 발급
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            String id = loginRequestDto.getId();
            // 아이디와 비밀번호 인증
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(id, loginRequestDto.getPw()));

            // 권한 설정
            String authority;
            MemberDto userInfo = memberService.DirectorInfo(loginRequestDto.getId());
            if (userInfo == null) {
                authority = "ROLE_MANAGER";
                userInfo = memberService.ManagerInfo(loginRequestDto.getId());
            } else {
                authority = "ROLE_DIRECTOR";
            }

            // 토큰 발급
            Long memberId = userInfo.getMemberNo();
            String accessToken = jwtTokenProvider.createAccessToken(id, authority, memberId);
            String refreshToken = jwtTokenProvider.createRefreshToken(id);
            ResponseEntity<Map<String, Object>> signInResponse = memberService.signIn(loginRequestDto, refreshToken, authority);

            Map<String, Object> response = signInResponse.getBody();
            if (signInResponse.getStatusCodeValue() == 200) {
                response.put("accessToken", accessToken);
                response.put("refreshToken", refreshToken);
                response.put("accessTokenExpireTime", jwtTokenProvider.getTokenExpireTime(accessToken));
                response.put("refreshTokenExpireTime", jwtTokenProvider.getTokenExpireTime(refreshToken));
            }
            response.put("status", signInResponse.getStatusCodeValue());

            return new ResponseEntity<>(response, signInResponse.getStatusCode());
        } catch (AuthenticationException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
            response.put("status", HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // 액세스 토큰 만료시 리프레시 토큰으로 액세스 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refresh(@RequestHeader String authorization) {
        String token = authorization.replace("Bearer ", "");
        Map<String, Object> response = new HashMap<>();

        // 현재 리프레시 토큰과 새로운 액세스 토큰
        String newAccessToken;
        try {
            newAccessToken = jwtTokenProvider.refreshAccessToken(token);
        } catch (Exception e) {
            response.put("message", "리프레시 토큰이 올바르지 않습니다.");
            response.put("status", HttpStatus.UNAUTHORIZED.value());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        response.put("accessToken", newAccessToken);
        response.put("message", "액세스 토큰을 성공적으로 발급하였습니다.");
        response.put("status", HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    // 이메일 인증 코드 보내기
    @PostMapping("/sendemail")
    public ResponseEntity<Map<String, Object>> requestEmailVerification(@RequestBody EmailVerificationDto requestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            memberService.requestEmailVerification(requestDto.getEmail());
            resultMap.put("message", "이메일 인증 코드가 발송되었습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage().split(": ")[1]);
            resultMap.put("status", Integer.parseInt(e.getMessage().split(": ")[0]));
            return new ResponseEntity<>(resultMap, HttpStatus.valueOf(Integer.parseInt(e.getMessage().split(": ")[0])));
        }
    }

    // 이메일 인증 코드 확인
    @PostMapping("/sendemail/verify")
    public ResponseEntity<Map<String, Object>> confirmEmailVerification(@RequestBody EmailVerificationConfirmationDto confirmationDto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            memberService.confirmEmailVerification(confirmationDto.getEmail(), confirmationDto.getCode());
            resultMap.put("message", "이메일이 성공적으로 인증되었습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    // 감독관 또는 운영자 정보 조회
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> memberInfo(@RequestBody MemberInfoDto memberInfoDto) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("member", memberService.UserInfo(memberInfoDto.getId()));
        resultMap.put("message", "성공적으로 조회하였습니다.");
        resultMap.put("status", 200);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @PutMapping("/info/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody MemberUpdateDto memberUpdateByUserDto, @RequestHeader String authorization) {
        String token = authorization.replace("Bearer ", "");
        String id = jwtTokenProvider.getId(token);
        String authority = jwtTokenProvider.getAuthority(token);
        Map<String, Object> resultMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> updateResponse = memberService.updateMemberByUser(id, authority, memberUpdateByUserDto);

        return updateResponse;
    }

    @PutMapping("/info/updatepassword")
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody MemberUpdatePasswordDto requestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            memberService.changePassword(requestDto);
            resultMap.put("message", "비밀번호가 성공적으로 변경되었습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody MemberInfoDto memberInfoDto, @RequestHeader String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        String token = authorization.replace("Bearer ", "");
        String id = memberInfoDto.getId();
        String authority = jwtTokenProvider.getAuthority(token);
        if (authority.equals("ROLE_DIRECTOR")) {
            if (!id.equals(jwtTokenProvider.getId(token))) {
                resultMap.put("message", "접근 권한이 없습니다.");
                resultMap.put("status", HttpStatus.UNAUTHORIZED.value());
                return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
            }
        }
        memberService.deleteMember(id);
        resultMap.put("message", "성공적으로 삭제되었습니다.");
        resultMap.put("status", HttpStatus.OK.value());
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @GetMapping("/findid")
    public ResponseEntity<Map<String, Object>> findId(@RequestBody FindIdDto findIdDto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("message", "성공적으로 조회하였습니다.");
            resultMap.put("data", memberService.findId(findIdDto));
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/findpassword")
    public ResponseEntity<Map<String, Object>> findPassword(@RequestBody FindPasswordDto findPasswordDto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            memberService.findPassword(findPasswordDto);
            resultMap.put("message", "등록하신 이메일로 임시 비밀번호를 전송하였습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }
}
