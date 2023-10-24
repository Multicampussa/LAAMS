package multicampussa.laams.manager.controller.center;

import multicampussa.laams.manager.dto.center.CenterMonthlyExamCountsResponse;
import multicampussa.laams.manager.service.center.CenterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CenterController {

    private final CenterService centerService;

    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    // 센터별 시험 횟수(한달)
    @GetMapping("/manager/center/{centerNo}/{year}/{month}")
    public CenterMonthlyExamCountsResponse getMonthlyExamCounts(@PathVariable Long centerNo, @PathVariable int year,@PathVariable int month) {
        return centerService.getMonthlyExamCounts(centerNo, year,month);
    }


}
