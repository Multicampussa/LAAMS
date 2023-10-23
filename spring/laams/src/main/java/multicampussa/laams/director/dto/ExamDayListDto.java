package multicampussa.laams.director.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.manager.domain.exam.Exam;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamDayListDto {

    private Long examNo;
    private LocalDateTime examDate;
    private String examType;
    private String examLanguage;
    private String centerName;
    private String centerRegion;

    public ExamDayListDto(Exam exam){
        this.examNo = exam.getNo();
        this.examDate = exam.getExamDate();
        this.examType = exam.getExamType();
        this.examLanguage = exam.getExamLanguage();
        this.centerName = exam.getCenter().getName();
        this.centerRegion = exam.getCenter().getRegion();
    }
}
