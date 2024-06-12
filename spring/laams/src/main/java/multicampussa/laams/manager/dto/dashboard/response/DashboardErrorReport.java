package multicampussa.laams.manager.dto.dashboard.response;

import lombok.Getter;
import multicampussa.laams.director.domain.errorReport.ErrorReport;

@Getter
public class DashboardErrorReport {
    Long errorReportNo;
    Long directorNo;
    String directorName;
    String title;
    String errorType;

    public DashboardErrorReport(ErrorReport errorReport) {
        this.errorReportNo = errorReport.getNo();
        this.directorNo = errorReport.getMember().getNo();
        this.directorName = errorReport.getMember().getName();
        this.title = errorReport.getTitle();
        this.errorType = errorReport.getType();
    }
}
