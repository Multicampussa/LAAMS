package multicampussa.laams.manager.controller.exam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.global.ApiResponse;
import multicampussa.laams.global.CustomExceptions;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.manager.dto.exam.request.ExamCreateRequest;
import multicampussa.laams.manager.dto.exam.request.ExamUpdateRequest;
import multicampussa.laams.manager.dto.exam.response.ExamDetailResponse;
import multicampussa.laams.manager.dto.exam.response.ExamResponse;
import multicampussa.laams.manager.service.exam.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "운영자의 시험 관련 기능")
@RestController
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;
    private final JwtTokenProvider jwtTokenProvider;

    // 시험 생성
    @ApiOperation("시험 생성")
    @PostMapping("/api/v1/manager/exam")
    public ResponseEntity<ApiResponse<String>> saveExam(
            @ApiIgnore @RequestHeader String authorization,
            @RequestBody ExamCreateRequest request) {

        String token = authorization.replace("Bearer", "");
        String authority = jwtTokenProvider.getAuthority(token);

        if (authority.equals("ROLE_MANAGER")) {
            examService.saveExam(request);
            return new ResponseEntity<>(new ApiResponse<>(
                    "success",
                    HttpStatus.OK.value(),
                    "시험이 성공적으로 생성되었습니다."), HttpStatus.OK);
        } else {
            throw new CustomExceptions.UnauthorizedException("접근 권한이 없습니다.");
        }


    }

    // 시험 목록 조회
    @ApiOperation("시험 목록 조회")
    @GetMapping("/api/v1/manager/exams")
    public ResponseEntity<ApiResponse<List<ExamResponse>>> getExams(
            @ApiIgnore @RequestHeader String authorization
    ) {
        String token = authorization.replace("Bearer", "");
        String authority = jwtTokenProvider.getAuthority(token);

        if (authority.equals("ROLE_MANAGER")) {
            return new ResponseEntity<>(new ApiResponse<>(
                    "success",
                    HttpStatus.OK.value(),
                    examService.getExams()), HttpStatus.OK);

        } else {
            throw new CustomExceptions.UnauthorizedException("접근 권한이 없습니다.");
        }
    }

    // 시험 상세 조회
    @ApiOperation("시험 상세 조회")
    @GetMapping("/api/v1/manager/exam/{no}")
    public ResponseEntity<ApiResponse<ExamDetailResponse>> getExam(
            @ApiIgnore @RequestHeader String authorization,
            @PathVariable Long no) {

        String token = authorization.replace("Bearer", "");
        String authority = jwtTokenProvider.getAuthority(token);

        if (authority.equals("ROLE_MANAGER")) {
            return new ResponseEntity<>(new ApiResponse<>(
                    "success",
                    HttpStatus.OK.value(),
                    examService.getExam(no)), HttpStatus.OK);
        } else {
            throw new CustomExceptions.UnauthorizedException("접근 권한이 없습니다.");
        }
    }

    // 월별 시험 목록 조회
    @ApiOperation("월별 시험 목록 조회")
    @GetMapping("/api/v1/manager/exam/monthly")
    public List<ExamResponse> getMonthlyExams(
            @ApiIgnore @RequestHeader String authorization,
            @RequestParam Integer year,
            @RequestParam Integer month)
    {
        String token = authorization.replace("Bearer", "");
        String authority = jwtTokenProvider.getAuthority(token);

        if (authority.equals("ROLE_MANAGER")) {
            return examService.getMonthlyExams(year, month);
        } else {
            throw new CustomExceptions.UnauthorizedException("접근 권한이 없습니다.");
        }

    }

    // 일별 시험 목록 조회
    @ApiOperation("일별 시험 목록 조회")
    @GetMapping("/api/v1/manager/exam/daily")
    public List<ExamResponse> getDailyExams(
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestParam Integer day)
    {
        return examService.getDailyExams(year, month, day);
    }

    // 시험 수정
    @ApiOperation("시험 수정")
    @PutMapping("/api/v1/manager/exam/{examId}")
    public ResponseEntity<String> updateExam(
            @PathVariable Long examId,
            @RequestBody ExamUpdateRequest request) {
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
