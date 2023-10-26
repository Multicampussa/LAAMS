package multicampussa.laams.manager.dto.exam.response;

import lombok.Getter;
import multicampussa.laams.manager.domain.exam.Exam;

import java.time.LocalDateTime;

@Getter
public class ExamResponse {

    private Long no;
    private String centerName;
    private LocalDateTime examDate;
    private Long managerNo;
    private String managerName;
    private String examType;
    public String examLanguage;

    public ExamResponse(Exam exam) {
        this.no = exam.getNo();
        this.centerName = exam.getCenter().getName();
        this.examDate = exam.getExamDate();
        this.managerNo = exam.getManager().getNo();
        this.managerName = exam.getManager().getName();
        this.examType = exam.getExamType();
        this.examLanguage = exam.getExamLanguage();
    }

}
