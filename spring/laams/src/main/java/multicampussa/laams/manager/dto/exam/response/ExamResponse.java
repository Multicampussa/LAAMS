package multicampussa.laams.manager.dto.exam.response;

import lombok.Getter;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.center.Center;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class ExamResponse {

    private Long no;
    private Center center;
    private LocalDateTime examDate;

    public ExamResponse(Exam exam) {
        this.no = exam.getNo();
        this.center = exam.getCenter();
        this.examDate = exam.getExamDate();
    }
}
