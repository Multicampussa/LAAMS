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
        String token  = authorization.replace("Bearer ", "");
        Long memberId = jwtTokenProvider.getMemberNo(token);

        noticeService.createNotice(noticeCreateDto, memberId);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", "성공적으로 작성하였습니다.");
        resultMap.put("status", HttpStatus.OK.value());
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
