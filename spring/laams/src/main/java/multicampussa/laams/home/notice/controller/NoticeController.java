package multicampussa.laams.home.notice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.home.notice.dto.*;
import multicampussa.laams.home.notice.service.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Api(tags = "공지사항 관련 기능")
public class NoticeController {

    private final NoticeService noticeService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "공지사항 생성")
    @PostMapping("/notice/create")
    public ResponseEntity<Map<String, Object>> createNotice(@RequestHeader String authorization, @RequestBody NoticeCreateDto noticeCreateDto, @RequestPart(value = "file", required = false) MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String token  = authorization.replace("Bearer ", "");
            Long memberNo = jwtTokenProvider.getMemberNo(token);
            String authority = jwtTokenProvider.getAuthority(token);

            boolean result = noticeService.createNotice(noticeCreateDto, memberNo, authority, file);

            resultMap.put("message", "성공적으로 작성하였습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "공지사항 수정")
    @PutMapping("/notice/update")
    public ResponseEntity<Map<String, Object>> updateNotice(@RequestHeader String authorization, @RequestBody NoticeUpdateDto noticeUpdateDto, @RequestPart(value = "file", required = false) MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        BaseResultDTO resultDTO = new BaseResultDTO();

        /**
         * 각 상황에 맞게 BaseResultDTO을 생성해서
         * 마지막 코드 줄에 return
         * return 문은 한 개만 있는게 좋다.
         */
        try{
            String token  = authorization.replace("Bearer ", "");
            Long memberNo = jwtTokenProvider.getMemberNo(token);
            String authority = jwtTokenProvider.getAuthority(token);

            boolean result = noticeService.updateNotice(noticeUpdateDto, memberNo, authority, file);

            resultMap.put("message", "성공적으로 수정하였습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "공지사항 삭제")
    @DeleteMapping("/notice/delete/{noticeNo}")
    public ResponseEntity<Map<String, Object>> deleteNotice(@RequestHeader String authorization, @PathVariable Long noticeNo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String token  = authorization.replace("Bearer ", "");
            Long memberNo = jwtTokenProvider.getMemberNo(token);
            String authority = jwtTokenProvider.getAuthority(token);

            boolean result = noticeService.deleteNotice(noticeNo, memberNo, authority);

            resultMap.put("message", "성공적으로 삭제하였습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }

    }

    // 특정 페이지 전체 공지사항 리스트 불러오기
    @ApiOperation(value = "공지사항 페이지네이션 된 리스트 불러오기")
    @GetMapping("/notice/list")
    public ResponseEntity<Map<String, Object>> getNoticeList(@RequestParam(value = "count", defaultValue = "10") int count, @RequestParam(value = "page", defaultValue = "1") int page) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{

            List<NoticeListResDto> noticeList= noticeService.getNoticeList(count, page);

            resultMap.put("message", "성공적으로 리스트를 불러왔습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            resultMap.put("data", noticeList);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    // 공지사항 상세조회
    @ApiOperation(value = "공지사항 상세 조회")
    @GetMapping("/notice/detail/{noticeNo}")
    public ResponseEntity<Map<String, Object>> getNoticeDetail(@PathVariable Long noticeNo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{

            NoticeDetailResDto notice= noticeService.getNoticeDetail(noticeNo);

            resultMap.put("message", "성공적으로 공지사항을 불러왔습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            resultMap.put("data", notice);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    // 공지사항 전체 개수 조회
    @ApiOperation(value = "공지사항 총 개수 조회")
    @GetMapping("/notice/count")
    public ResponseEntity<Map<String, Object>> getNoticeCount() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            Map<String, Object> countData = new HashMap<String, Object>();
            int noticeCount = noticeService.getNoticeCount();

            countData.put("count", noticeCount);

            resultMap.put("message", "성공적으로 공지사항 총 개수를 불러왔습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            resultMap.put("data", countData);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }


}
