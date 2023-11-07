package multicampussa.laams.director.dto.director;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamDirector;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnappliedAndUnapprovedExamListDto {

    private Long examNo;
    private LocalDateTime examDate;
    private int runningTime;
    private LocalDateTime endExamDate;
    private String examType;
    private String examLanguage;
    private String currentConfirm;
    private int confirmDirectorCnt;

    public UnappliedAndUnapprovedExamListDto(Exam exam, String currentConfirm, int cntConfirmDirector){
        this.examNo = exam.getNo();
        this.examDate = exam.getExamDate();
        this.runningTime = exam.getRunningTime();
        this.endExamDate = exam.getExamDate().plus(exam.getRunningTime(), ChronoUnit.MINUTES);
        this.examType = exam.getExamType();
        this.examLanguage = exam.getExamLanguage();
        this.currentConfirm = currentConfirm;
        this.confirmDirectorCnt = cntConfirmDirector;

    }
}
