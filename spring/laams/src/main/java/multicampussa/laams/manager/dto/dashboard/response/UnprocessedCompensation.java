package multicampussa.laams.manager.dto.dashboard.response;

import lombok.Getter;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;

@Getter
public class UnprocessedCompensation {
    Long examineeNo;
    String examineeName;
    String compensationType;

    public UnprocessedCompensation(ExamExaminee examExaminee) {
        this.examineeNo = examExaminee.getExaminee().getNo();
        this.examineeName = examExaminee.getExaminee().getName();
        this.compensationType = examExaminee.getCompensationType();
    }
}
