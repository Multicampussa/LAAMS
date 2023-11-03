package multicampussa.laams.manager.dto.dashboard.response;

import lombok.Getter;
import multicampussa.laams.director.domain.errorReport.ErrorReport;
import multicampussa.laams.manager.domain.exam.Exam;

@Getter
public class UnassignedExam {

    private Long examNo;
    private String centerName;
    private String centerManagerName;

    public UnassignedExam(Exam exam) {
        this.examNo = exam.getNo();
        this.centerName = exam.getCenter().getName();
        this.centerManagerName = exam.getCenter().getCenterManager().getName();
    }
}
