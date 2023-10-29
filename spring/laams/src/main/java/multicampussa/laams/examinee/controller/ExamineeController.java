package multicampussa.laams.examinee.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import multicampussa.laams.examinee.dto.request.EnrollExamRequest;
import multicampussa.laams.examinee.dto.response.CenterExamsResponse;
import multicampussa.laams.examinee.service.ExamineeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "응시자 관련 기능")
@RestController
public class ExamineeController {

    private final ExamineeService examineeService;

    public ExamineeController(ExamineeService examineeService) {
        this.examineeService = examineeService;
    }

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

}
