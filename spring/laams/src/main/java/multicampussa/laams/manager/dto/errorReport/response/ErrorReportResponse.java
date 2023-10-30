package multicampussa.laams.manager.dto.errorReport.response;

import lombok.Getter;
import multicampussa.laams.director.domain.ErrorReport.ErrorReport;

@Getter
public class ErrorReportResponse {

    private String title;
    private String directorName;
    private String errorType;

    public ErrorReportResponse(ErrorReport errorReport) {
        this.title = errorReport.getTitle();
        this.directorName = errorReport.getDirector().getName();
        this.errorType = errorReport.getType();
    }
}
