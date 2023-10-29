package multicampussa.laams.manager.dto.center.response;

import lombok.Getter;
import multicampussa.laams.manager.domain.center.Center;

@Getter
public class CenterResponse {

    private Long centerNo;
    private String centerName;
    private String centerRegion;

    public CenterResponse(Center center) {
        this.centerNo = center.getNo();
        this.centerName = center.getName();
        this.centerRegion = center.getRegion();
    }
}
