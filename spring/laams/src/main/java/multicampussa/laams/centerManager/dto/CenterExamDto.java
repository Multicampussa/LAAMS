package multicampussa.laams.centerManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.manager.domain.exam.ExamDirector;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CenterExamDto {

//    private Long examDirectorNo;
    private String examDirectorName;
    private ExamDirector.Confirm confirm;

    public CenterExamDto(ExamDirector examDirector) {
        this.examDirectorName = examDirector.getDirector().getName();
        this.confirm = examDirector.getConfirm();
    }
}
