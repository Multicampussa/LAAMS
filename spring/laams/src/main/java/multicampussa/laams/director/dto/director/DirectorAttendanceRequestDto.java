package multicampussa.laams.director.dto.director;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectorAttendanceRequestDto {
    private Double latitude;
    private Double longitude;
}
