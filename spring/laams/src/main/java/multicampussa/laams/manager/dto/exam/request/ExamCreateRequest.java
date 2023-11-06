package multicampussa.laams.manager.dto.exam.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExamCreateRequest {

    private String centerName;
    private LocalDateTime examDate;
    private Long managerNo;
    private int runningTime;
    private String examType;
    private String examLanguage;
    private int maxDirector;
}
