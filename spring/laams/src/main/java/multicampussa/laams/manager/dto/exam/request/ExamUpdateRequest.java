package multicampussa.laams.manager.dto.exam.request;

import lombok.Getter;
import multicampussa.laams.manager.domain.exam.center.Center;

import java.time.LocalDateTime;

@Getter
public class ExamUpdateRequest {

    private String centerName;
    private LocalDateTime examDate;
    private Long managerNo;
    private Long examNo;
}
