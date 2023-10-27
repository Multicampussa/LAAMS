package multicampussa.laams.manager.controller.exam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.manager.dto.exam.request.ExamCreateRequest;
import multicampussa.laams.manager.dto.exam.request.ExamUpdateRequest;
import multicampussa.laams.manager.dto.exam.response.ExamDetailResponse;
import multicampussa.laams.manager.dto.exam.response.ExamResponse;
import multicampussa.laams.manager.service.exam.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "시험 관련")
@RestController
public class ExamController {

    private final ExamService examService;
    private final JwtTokenProvider jwtTokenProvider;

    public ExamController(ExamService examService, JwtTokenProvider jwtTokenProvider) {
        this.examService = examService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 시험 생성
    @ApiOperation("시험 생성")
    @PostMapping("/api/v1/manager/exam")
    public ResponseEntity<String> saveExam(@RequestBody ExamCreateRequest request) {
        examService.saveExam(request);
        return ResponseEntity.ok("시험이 성공적으로 생성되었습니다.");
    }

    // 시험 목록 조회
    @ApiOperation("시험 목록 조회")
    @GetMapping("/api/v1/manager/exams")
    public List<ExamResponse> getExams() {
        return examService.getExams();
    }

    // 시험 상세 조회
    @ApiOperation("시험 상세 조회")
    @GetMapping("/api/v1/manager/exam/{no}")
    public ExamDetailResponse getExam(@PathVariable Long no) {
        return examService.getExam(no);
    }

    // 월별 시험 목록 조회
    @ApiOperation("월별 시험 목록 조회")
    @GetMapping("/api/vi/manager/exam/{year}/{month}")
    public List<ExamResponse> getMonthlyExams(@PathVariable Integer year, @PathVariable Integer month) {
        return examService.getMonthlyExams(year, month);
    }

    // 일별 시험 목록 조회
    @ApiOperation("일별 시험 목록 조회")
    @GetMapping("/api/vi/manager/exam/{year}/{month}/{day}")
    public List<ExamResponse> getDailyExams(@PathVariable Integer year, @PathVariable Integer month, @PathVariable Integer day) {
        return examService.getDailyExams(year, month, day);
    }

    // 시험 수정
    @ApiOperation("시험 수정")
    @PutMapping("/api/v1/manager/exam/{examId}")
    public ResponseEntity<String> updateExam(@PathVariable Long examId, @RequestBody ExamUpdateRequest request) {
        examService.updateExam(examId, request);
        return ResponseEntity.ok("시험이 성공적으로 수정되었습니다.");
    }

    // 시험 삭제
    @ApiOperation("시험 삭제")
    @DeleteMapping("/api/v1/manager/exam/{no}")
    public void deleteExam(@PathVariable Long no) {
        examService.deleteExam(no);
    }
}
