package multicampussa.laams.manager.service.errorReport;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.domain.ErrorReport.ErrorReport;
import multicampussa.laams.director.repository.errorReport.ErrorReportRepository;
import multicampussa.laams.global.CustomExceptions;
import multicampussa.laams.manager.dto.errorReport.response.ErrorReportDetailResponse;
import multicampussa.laams.manager.dto.errorReport.response.ErrorReportResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ErrorReportService {

    private final ErrorReportRepository errorReportRepository;

    public ErrorReportService(ErrorReportRepository errorReportRepository) {
        this.errorReportRepository = errorReportRepository;
    }

    // 에러 리포트 목록 조회
    @Transactional(readOnly = true)
    public List<ErrorReportResponse> getErrorReports() {
        List<ErrorReport> errorReports = errorReportRepository.findAll();

        return errorReports.stream()
                .map(ErrorReportResponse::new)
                .collect(Collectors.toList());
    }


    // 에러 리포트 상세 조회
    @Transactional(readOnly = true)
    public ErrorReportDetailResponse getErrorReport(Long no) {
        ErrorReport errorReport = errorReportRepository.findByNo(no);
        if (errorReport == null) {
            throw new CustomExceptions.ErrorReportNotFoundException(no + "번 에러리포트는 존재하지 않습니다.");
        }

        return new ErrorReportDetailResponse(errorReport);
    }
}
