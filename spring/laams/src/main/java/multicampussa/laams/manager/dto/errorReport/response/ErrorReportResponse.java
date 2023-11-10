package multicampussa.laams.manager.dto.errorReport.response;

import lombok.Getter;
import multicampussa.laams.director.domain.errorReport.ErrorReport;

import java.time.LocalDateTime;

@Getter
public class ErrorReportResponse {

    private Long errorReportNo;
    private String title;
    private String directorName;
    private String errorType;
    private LocalDateTime errorTime;

    public ErrorReportResponse(ErrorReport errorReport) {
        this.errorReportNo = errorReport.getNo();
        this.title = errorReport.getTitle();
        this.directorName = errorReport.getDirector().getName();
        this.errorType = errorReport.getType();
        this.errorTime = errorReport.getErrorTime();
    }
}
