package multicampussa.laams.examinee.dto.response;

import lombok.Getter;
import multicampussa.laams.manager.domain.exam.Exam;

import java.time.LocalDateTime;

@Getter
public class CenterExamsResponse {

    private String centerName;
    private Long centerNo;
    private String centerRegion;
    private String examType;
    private LocalDateTime examDate;


    public CenterExamsResponse(Exam exam) {
        this.centerName = exam.getCenter().getName();
        this.centerNo = exam.getCenter().getNo();
        this.centerRegion = exam.getCenter().getRegion();
        this.examType = exam.getExamType();
        this.examDate = exam.getExamDate();
    }
}
