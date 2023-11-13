package multicampussa.laams.examinee.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.examinee.dto.request.EnrollExamRequest;
import multicampussa.laams.examinee.dto.response.CenterExamsResponse;
import multicampussa.laams.examinee.service.ExamineeService;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "응시자 관련 기능")
@RestController
@RequiredArgsConstructor
public class ExamineeController {

    private final ExamineeService examineeService;
    private final JwtTokenProvider jwtTokenProvider;


    // 시험 응시 신청
    @ApiOperation("시험 응시 신청")
    @PostMapping("/api/v1/examinee/exam")
    public ResponseEntity<String> enrollExam(@RequestBody EnrollExamRequest enrollExamRequest) {
        examineeService.enrollExam(enrollExamRequest);
        return ResponseEntity.ok(enrollExamRequest.getExamNo() + "번 시험에 " + enrollExamRequest.getExamineeNo() + "번 응시자가 등록되었습니다.");
    }

    // 센터별 시험 목록 조회
    @ApiOperation("센터별 시험 목록 조회")
    @GetMapping("/api/v1/examinee/center/{centerNo}/exams")
    public List<CenterExamsResponse> getCenterExams(@PathVariable Long centerNo) {
        return examineeService.getCenterExams(centerNo);
    }

    // 응시자 출석 업데이트
    @ApiOperation("응시자 출석")
    @PostMapping("/api/v1/examinee/attendance")
    public ResponseEntity<Map<String, Object>> UpdateExamineeAttendance(@RequestHeader String authorization){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String token  = authorization.replace("Bearer ", "");
            String authority = jwtTokenProvider.getAuthority(token);
            String examineeCode = jwtTokenProvider.getCode(token);

            examineeService.updateExamineeAttendance(authority, examineeCode);
            resultMap.put("message", "응시자 출석 성공했습니다.");
            resultMap.put("code", HttpStatus.OK.value());
            resultMap.put("status", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e){
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    
}

