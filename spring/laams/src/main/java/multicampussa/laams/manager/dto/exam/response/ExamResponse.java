package multicampussa.laams.manager.dto.exam.response;

import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.center.Center;

import java.time.LocalDateTime;

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
