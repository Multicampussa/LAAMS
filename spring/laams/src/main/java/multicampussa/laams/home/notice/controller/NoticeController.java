package multicampussa.laams.home.notice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.home.notice.domain.Notice;
import multicampussa.laams.home.notice.dto.*;
import multicampussa.laams.home.notice.service.NoticeService;
import multicampussa.laams.home.notice.service.S3Service;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Api(tags = "공지사항 관련 기능")
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
    private final JwtTokenProvider jwtTokenProvider;

    private final S3Service s3Service;

    @ApiOperation(value = "공지사항 생성")
    @PostMapping(value="/notice/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> createNotice(@RequestHeader String authorization, @RequestPart(value = "dto") NoticeCreateDto noticeCreateDto, @RequestPart(value = "file", required = false) MultipartFile file) {
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
    @PutMapping(value="/notice/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> updateNotice(@RequestHeader String authorization, @RequestPart(value = "dto") NoticeUpdateDto noticeUpdateDto, @RequestPart(value = "file", required = false) MultipartFile file) {
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

            boolean result = noticeService.updateNotice(noticeUpdateDto, authority, file);

            resultMap.put("message", "성공적으로 수정하였습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            log.error(e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage());
            return  null;
        }
    }

    @ApiOperation(value = "공지사항 삭제")
    @PutMapping("/notice/delete/{noticeNo}")
    public ResponseEntity<Map<String, Object>> deleteNotice(@RequestHeader String authorization, @PathVariable Long noticeNo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String token  = authorization.replace("Bearer ", "");
//            Long memberNo = jwtTokenProvider.getMemberNo(token);
            String authority = jwtTokenProvider.getAuthority(token);

            boolean result = noticeService.deleteNotice(noticeNo, authority);

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


    @ApiOperation(value = "공지사항 첨부파일 다운로드")
    @GetMapping("/notice/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) {

        try {
            // 파일을 스트림으로 전송하고 다운로드 가능하도록 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);

            InputStream fileInputStream = noticeService.downloadFile(fileName).getBody().getInputStream();
            return new ResponseEntity<>(new InputStreamResource(fileInputStream), headers, HttpStatus.OK);
        } catch (IOException e) {
            // 예외 발생 시, 사용자에게 에러 메시지를 반환
            return new ResponseEntity<>("NOT_FOUND_FILE", HttpStatus.NOT_FOUND);
        }
    }


//    @ApiOperation(value = "공지사항 첨부파일 다운로드")
//    @GetMapping("/notice/download/{fileName}")
//    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName) throws NotFoundException {
//
//        // 파일을 스트림으로 전송하고 다운로드 가능하도록 헤더 설정
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", fileName);
//
//        InputStream fileInputStream = null;
//        try {
//            fileInputStream = noticeService.downloadFile(fileName).getBody().getInputStream();
//        } catch (IOException e) {
//            // Empty File InputStream
//            InputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);
//            InputStreamResource emptyInputStreamResource = new InputStreamResource(emptyInputStream);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyInputStreamResource);
//        }
//
//        return new ResponseEntity<>(new InputStreamResource(fileInputStream), headers, HttpStatus.OK);
//    }

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
