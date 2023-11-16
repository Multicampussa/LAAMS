package multicampussa.laams.home.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RealTimeDashboardResDto {
    List<RealTimeDashboardDto> realTimeDashboard;

}
