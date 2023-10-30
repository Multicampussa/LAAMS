package multicampussa.laams.director.dto.Director;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.manager.domain.exam.Exam;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamInformationDto {

    private Long examNo;
    private LocalDateTime examDate;
    private int runningTime;
    private String examType;
    private String examLanguage;
    private String centerName;
    private String centerRegion;

    public ExamInformationDto(Exam exam){
        this.examNo = exam.getNo();
        this.examDate = exam.getExamDate();
        this.runningTime = exam.getRunningTime();
        this.examType = exam.getExamType();
        this.examLanguage = exam.getExamLanguage();
        this.centerName = exam.getCenter().getName();
        this.centerRegion = exam.getCenter().getRegion();
    }

}
