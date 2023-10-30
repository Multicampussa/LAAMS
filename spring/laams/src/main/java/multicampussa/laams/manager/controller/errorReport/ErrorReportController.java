package multicampussa.laams.manager.controller.errorReport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.dto.errorReport.response.ErrorReportDetailResponse;
import multicampussa.laams.manager.dto.errorReport.response.ErrorReportResponse;
import multicampussa.laams.manager.service.errorReport.ErrorReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "운영자의 에러리포트 관련 기능")
@RestController
public class ErrorReportController {

    private ErrorReportService errorReportService;

    public ErrorReportController(ErrorReportService errorReportService) {
        this.errorReportService = errorReportService;
    }

    // 에러 리포트 목록 조회
    @ApiOperation("에러리포트 목록 조회")
    @GetMapping("/api/v1/manager/errorreport")
    public List<ErrorReportResponse> getErrorReports() {
        return errorReportService.getErrorReports();
    }


    // 에러 리포트 상세 조회
    @ApiOperation("에러리포트 상세 조회")
    @GetMapping("/api/v1/manager/errorreport/{no}")
    public ErrorReportDetailResponse getErrorReport(@PathVariable Long no) {
        return errorReportService.getErrorReport(no);
    }

}
