package multicampussa.laams.manager.controller.errorReport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.global.ApiResponse;
import multicampussa.laams.global.CustomExceptions;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.dto.errorReport.response.ErrorReportDetailResponse;
import multicampussa.laams.manager.dto.errorReport.response.ErrorReportResponse;
import multicampussa.laams.manager.service.errorReport.ErrorReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "운영자의 에러리포트 관련 기능")
@RestController
@RequiredArgsConstructor
public class ErrorReportController {

    private ErrorReportService errorReportService;
    private JwtTokenProvider jwtTokenProvider;

    // 에러 리포트 목록 조회
    @ApiOperation("에러리포트 목록 조회")
    @GetMapping("/api/v1/manager/errorreport")
    public ResponseEntity<ApiResponse<List<ErrorReportResponse>>> getErrorReports(
            @ApiIgnore @RequestHeader String authorization
    ) {

        String token = authorization.replace("Bearer", "");
        String authority = jwtTokenProvider.getAuthority(token);

        if (authority.equals("ROLE_MANAGER")) {
            return new ResponseEntity<>(new ApiResponse<>(
                    "success",
                    HttpStatus.OK.value(),
                    errorReportService.getErrorReports()), HttpStatus.OK);
        } else {
            throw new CustomExceptions.UnauthorizedException("접근 권한이 없습니다.");
        }
    }


    // 에러 리포트 상세 조회
    @ApiOperation("에러리포트 상세 조회")
    @GetMapping("/api/v1/manager/errorreport/{no}")
    public ResponseEntity<ApiResponse<ErrorReportDetailResponse>> getErrorReport(
            @ApiIgnore @RequestHeader String authorization,
            @PathVariable Long no) {

        String token = authorization.replace("Bearer", "");
        String authority = jwtTokenProvider.getAuthority(token);

        if (authority.equals("ROLE_MANAGER")) {
            return new ResponseEntity<>(new ApiResponse<>(
                    "success",
                    HttpStatus.OK.value(),
                    errorReportService.getErrorReport(no)), HttpStatus.OK);
        } else {
            throw new CustomExceptions.UnauthorizedException("접근 권한이 없습니다.");
        }
    }

}
