package multicampussa.laams.manager.dto.dashboard.response;

import lombok.Getter;

import java.util.List;

@Getter
public class DashboardResponse {

    int day;
    List<DashboardErrorReport> dailyDashboardErrorReports;
    List<UnprocessedCompensation> unprocessedCompensations;
//    List<UnprocessedAssignment> unprocessedAssignments;
    List<UnassignedExam> unassignedExams;

    public DashboardResponse(int day,
                             List<DashboardErrorReport> dashboardErrorReports,
                             List<UnprocessedCompensation> unprocessedCompensations,
//                             List<UnprocessedAssignment> unprocessedAssignments,
                             List<UnassignedExam> unassignedExams
    ) {
        this.day = day;
        this.dailyDashboardErrorReports = dashboardErrorReports;
        this.unprocessedCompensations = unprocessedCompensations;
//        this.unprocessedAssignments = unprocessedAssignments;
        this.unassignedExams = unassignedExams;
    }
}