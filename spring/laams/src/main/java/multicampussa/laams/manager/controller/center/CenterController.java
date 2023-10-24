package multicampussa.laams.manager.controller.center;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import multicampussa.laams.manager.dto.center.CenterMonthlyExamCountsResponse;
import multicampussa.laams.manager.service.center.CenterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "센터 관련")
@RestController
public class CenterController {

    private final CenterService centerService;

    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    // 센터별 시험 횟수(한달)
    @ApiOperation(value = "센터의 월별 시험 횟수 조회")
    @GetMapping("/manager/center/{centerNo}/{year}/{month}")
    public CenterMonthlyExamCountsResponse getMonthlyExamCounts(@PathVariable Long centerNo, @PathVariable int year,@PathVariable int month) {
        return centerService.getMonthlyExamCounts(centerNo, year,month);
    }


}
