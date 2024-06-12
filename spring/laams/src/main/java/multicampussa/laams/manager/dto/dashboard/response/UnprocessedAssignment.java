package multicampussa.laams.manager.dto.dashboard.response;

import lombok.Getter;
import multicampussa.laams.manager.domain.exam.ExamDirector;

@Getter
public class UnprocessedAssignment {
    Long directorNo;
    String directorName;
    String examType;
    String examLanguage;

    public UnprocessedAssignment(ExamDirector examDirector) {
        this.directorNo = examDirector.getMember().getNo();
        this.directorName = examDirector.getMember().getName();
        this.examType = examDirector.getExam().getExamType();
        this.examLanguage = examDirector.getExam().getExamLanguage();
    }
}
