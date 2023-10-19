package multicampussa.laams.home.notice.controller;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.home.notice.dto.NoticeCreateDto;
import multicampussa.laams.home.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app")
public class NoticeController {

    private final NoticeService noticeService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/notice/create")
    public ResponseEntity<Map<String, Object>> createNotice(@RequestHeader String authorization,  @RequestBody NoticeCreateDto noticeCreateDto) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String token  = authorization.replace("Bearer ", "");
            Long memberId = jwtTokenProvider.getMemberNo(token);
            String authority = jwtTokenProvider.getAuthority(token);

            boolean result = noticeService.createNotice(noticeCreateDto, memberId, authority);

            resultMap.put("message", "성공적으로 작성하였습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }
}
