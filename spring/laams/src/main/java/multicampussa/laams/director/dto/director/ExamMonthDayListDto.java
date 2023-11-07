package multicampussa.laams.director.dto.director;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.manager.domain.exam.Exam;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamMonthDayListDto {

    private Long examNo;
    private LocalDateTime examDate;
    private int runningTime;
    private LocalDateTime endExamDate;
    private String examType;
    private String examLanguage;
    private String centerName;
    private String centerRegion;

    public ExamMonthDayListDto(Exam exam){
        this.examNo = exam.getNo();
        this.examDate = exam.getExamDate();
        this.runningTime = exam.getRunningTime();
        this.endExamDate = exam.getExamDate().plus(exam.getRunningTime(), ChronoUnit.MINUTES);
        this.examType = exam.getExamType();
        this.examLanguage = exam.getExamLanguage();
        this.centerName = exam.getCenter().getName();
        this.centerRegion = exam.getCenter().getRegion();
    }
}
