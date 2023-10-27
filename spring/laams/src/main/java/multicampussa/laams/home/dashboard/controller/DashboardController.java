package multicampussa.laams.home.dashboard.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.dashboard.service.DashboardService;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.home.notice.dto.BaseResultDTO;
import multicampussa.laams.home.notice.dto.NoticeCreateDto;
import multicampussa.laams.home.notice.dto.NoticeListResDto;
import multicampussa.laams.home.notice.dto.NoticeUpdateDto;
import multicampussa.laams.home.notice.service.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manager/dashboard")
@Api(tags = "대시보드")
public class DashboardController {

    private final DashboardService dashboardService;
    private final JwtTokenProvider jwtTokenProvider;


    // 센터별 시험 횟수(한달) 조회
    @ApiOperation(value = "센터별 월 시험 횟수 조회")
    @GetMapping("/{centerNo}/exam")
    public ResponseEntity<Map<String, Object>> getCenterExamMonthCount(@RequestHeader String authorization, @PathVariable Long centerNo, @RequestParam int year, @RequestParam int month) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String token  = authorization.replace("Bearer ", "");
            String authority = jwtTokenProvider.getAuthority(token);
            Map<String, Object> centerExamCount = new HashMap<String, Object>();

            centerExamCount = dashboardService.getCenterExamMonthCount(authority, centerNo, year, month);

            resultMap.put("message", "성공적으로 센터별 시험횟수를 불러왔습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            resultMap.put("data", centerExamCount);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    // 공지사항 상세조회
    @GetMapping("/notice/detail/{noticeNo}")
    public ResponseEntity<Map<String, Object>> getNoticeDetail(@PathVariable Long noticeNo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{

            NoticeListResDto notice= noticeService.getNoticeDetail(noticeNo);

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
