package multicampussa.laams.home.member.controller;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.member.dto.*;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.home.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            String email = loginRequestDto.getEmail();
            // 이메일과 비밀번호 인증
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, loginRequestDto.getPassword()));

            // 권한 설정
            MemberDto userInfo = memberService.AdminInfo(loginRequestDto.getEmail());
            String authority = "ROLE_ADMIN";

            // 토큰 발급
            Long memberId = userInfo.getMemberId();
            String accessToken = jwtTokenProvider.createAccessToken(email, authority, memberId);
            String refreshToken = jwtTokenProvider.createRefreshToken(email);
            ResponseEntity<Map<String, Object>> signInResponse = memberService.signIn(loginRequestDto, refreshToken);

            Map<String, Object> response = signInResponse.getBody();
            if (signInResponse.getStatusCodeValue() == 200) {
                response.put("accessToken", accessToken);
                response.put("refreshToken", refreshToken);
            }
            response.put("status", signInResponse.getStatusCodeValue());

            return new ResponseEntity<>(response, signInResponse.getStatusCode());
        } catch (AuthenticationException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "이메일 또는 비밀번호가 일치하지 않습니다.");
            response.put("status", HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // 이메일 인증 코드 보내기
    @PostMapping("/email-verification")
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
    @PostMapping("/email-verification/confirm")
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
}