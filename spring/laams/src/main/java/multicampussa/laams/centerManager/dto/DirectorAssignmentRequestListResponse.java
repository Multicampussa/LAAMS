package multicampussa.laams.centerManager.dto;

import lombok.Getter;
import multicampussa.laams.manager.domain.exam.ExamDirector;

@Getter
public class DirectorAssignmentRequestListResponse {
    private Long examNo;
    private Long directorNo;
    private String directorName;
    private ExamDirector.Confirm isConfirm;

    public DirectorAssignmentRequestListResponse(ExamDirector examDirector) {
        this.examNo = examDirector.getExam().getNo();
        this.directorNo = examDirector.getDirector().getNo();
        this.directorName = examDirector.getDirector().getName();
        this.isConfirm = examDirector.getConfirm();
    }
}
