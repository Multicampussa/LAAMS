package multicampussa.laams.director.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamManager;
import multicampussa.laams.manager.domain.exam.center.Center;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamListDto {

    private Long examNo;
    private LocalDateTime examDate;
    private String address;

    public ExamListDto(Exam exam){
        this.examNo = exam.getNo();
        this.examDate = exam.getExamDate();
        this.address = exam.getCenter().getAddress();
    }
}

