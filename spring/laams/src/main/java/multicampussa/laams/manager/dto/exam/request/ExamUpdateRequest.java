package multicampussa.laams.manager.dto.exam.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExamUpdateRequest {

    private String centerName;
    private LocalDateTime examDate;
    private Long managerNo;
    private Long examNo;
    private int runningTime;
    private String examType;
}
