package multicampussa.laams.director.dto.director;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckDocumentDto {

    private String document;
    private Boolean compensation;
    private String compensationType;
}
