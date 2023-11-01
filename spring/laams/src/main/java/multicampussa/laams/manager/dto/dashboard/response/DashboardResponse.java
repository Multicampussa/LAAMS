package multicampussa.laams.manager.dto.dashboard.response;

import multicampussa.laams.director.domain.director.Director;
import multicampussa.laams.manager.domain.exam.ExamDirector;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;

import java.util.List;

public class DashboardResponse {

    private class ErrorReport {
        Long directorNo;
        String directorName;
        String title;
        String errorType;

        public ErrorReport(ErrorReport errorReport) {
            this.directorNo = errorReport.directorNo;
            this.directorName = errorReport.directorName;
            this.title = errorReport.title;
            this.errorType = errorReport.errorType;
        }
    }

    private class UnprocessedCompensation {
        Long examineeNo;
        String examineeName;
        String compensationType;

        public UnprocessedCompensation(ExamExaminee examExaminee) {
            this.examineeNo = examExaminee.getExaminee().getNo();
            this.examineeName = examExaminee.getExaminee().getName();
            this.compensationType = examExaminee.getCompensationType();
        }
    }

    private class UnprocessedAssignment {
        Long directorNo;
        String directorName;
        String examType;
        String examLanguage;

        public UnprocessedAssignment(ExamDirector examDirector) {
            this.directorNo = examDirector.getDirector().getNo();
            this.directorName = examDirector.getDirector().getName();
            this.examType = examDirector.getExam().getExamType();
            this.examLanguage = examDirector.getExam().getExamLanguage();
        }
    }

    int day;
    List<ErrorReport> dailyErrorReports;
    List<UnprocessedCompensation> unprocessedCompensations;
    List<UnprocessedAssignment> unprocessedAssignments;

    public DashboardResponse(int day, List<ErrorReport> errorReports, List<UnprocessedCompensation> unprocessedCompensations, List<UnprocessedAssignment> unprocessedAssignments) {
        this.day = day;
        this.dailyErrorReports = errorReports;
        this.unprocessedCompensations = unprocessedCompensations;
        this.unprocessedAssignments = unprocessedAssignments;
    }
}