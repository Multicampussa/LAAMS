package multicampussa.laams.home.member.controller;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.member.dto.MemberSignUpDto;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.home.member.service.MemberService;
import org.springframework.http.ResponseEntity;
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
//    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody MemberSignUpDto memberSignUpDto) {
        System.out.println("오나?");
        ResponseEntity<String> result = memberService.signUp(memberSignUpDto);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", result.getBody());
        resultMap.put("status", result.getStatusCodeValue());
        return new ResponseEntity<Map<String, Object>>(resultMap, result.getStatusCode());
    }
}
