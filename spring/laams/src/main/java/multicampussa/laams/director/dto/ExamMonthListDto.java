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
public class ExamMonthListDto {

    private Long examNo;
    private LocalDateTime examDate;
    private String centerName;
    private String examType;
    private String examLanguage;

    public ExamMonthListDto(Exam exam){
        this.examNo = exam.getNo();
        this.examDate = exam.getExamDate();
        this.centerName = exam.getCenter().getName();
        this.examType = exam.getExamType();
        this.examLanguage = exam.getExamLanguage();
    }
}

