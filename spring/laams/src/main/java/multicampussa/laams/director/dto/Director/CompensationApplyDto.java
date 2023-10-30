package multicampussa.laams.director.dto.Director;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompensationApplyDto {
    private String compensationType;
    private String compensationReason;
}
