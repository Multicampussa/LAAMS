package multicampussa.laams.centerManager.dto;

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
public class CenterExamListDto {

    private Long examNo;
    private LocalDateTime examDate;
    private String examType;
    private String examLanguage;

    public CenterExamListDto(Exam exam){
        this.examNo  = exam.getNo();
        this.examDate = exam.getExamDate();
        this.examType = exam.getExamType();
        this.examLanguage = exam.getExamLanguage();
    }
}
