package multicampussa.laams.manager.dto.exam.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExamUpdateRequest {

    private String newCenterName;
    private LocalDateTime newExamDate;
    private Long newManagerNo;
    private int newRunningTime;
    private String newExamType;
    private int newMaxDirector;
}
