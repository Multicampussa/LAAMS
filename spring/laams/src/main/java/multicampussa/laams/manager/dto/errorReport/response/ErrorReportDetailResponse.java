package multicampussa.laams.manager.dto.errorReport.response;

import lombok.Getter;
import multicampussa.laams.director.domain.errorReport.ErrorReport;

import java.time.LocalDateTime;

@Getter
public class ErrorReportDetailResponse {

    private String title;
    private String content;
    private String errorType;
    private LocalDateTime errorTime;
    private String directorName;
    private LocalDateTime createdTime;

    public ErrorReportDetailResponse(ErrorReport errorReport) {
        this.title = errorReport.getTitle();
        this.content = errorReport.getContent();
        this.errorType = errorReport.getType();
        this.errorTime = errorReport.getErrorTime();
        this.directorName = errorReport.getDirector().getName();
        this.createdTime = errorReport.getCreatedAt();
    }
}
