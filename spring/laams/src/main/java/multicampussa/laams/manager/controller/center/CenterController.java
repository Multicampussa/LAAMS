package multicampussa.laams.manager.controller.center;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.global.CustomExceptions;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.manager.dto.center.response.CenterResponse;
import multicampussa.laams.manager.dto.director.response.CenterMonthlyExamCountsResponse;
import multicampussa.laams.manager.service.center.CenterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "운영자의 센터 관련 기능")
@RestController
@RequiredArgsConstructor
public class CenterController {

    private final CenterService centerService;
    private final JwtTokenProvider jwtTokenProvider;

    // 전체 센터 리스트 조회
    @ApiOperation(value = "전체 센터 리스트 조회")
    @GetMapping("/api/v1/manager/centers")
    public List<CenterResponse> getCenters(
            @ApiIgnore @RequestHeader String authorization
    ) {

        String token = authorization.replace("Bearer", "");
        String authority = jwtTokenProvider.getAuthority(token);

        if (authority.equals("ROLE_MANAGER")) {
            return centerService.getCenters();
        } else {
            throw new CustomExceptions.UnauthorizedException("접근 권한이 없습니다.");
        }
    }


    // 센터별 시험 횟수(한달) 조회
    @ApiOperation(value = "센터의 월별 시험 횟수 조회")
    @GetMapping("/api/v1/manager/center/{centerNo}/{year}/{month}")
    public CenterMonthlyExamCountsResponse getMonthlyExamCounts(
            @ApiIgnore @RequestHeader String authorization,
            @PathVariable Long centerNo,
            @PathVariable int year,
            @PathVariable int month) {

        String token = authorization.replace("Bearer", "");
        String authority = jwtTokenProvider.getAuthority(token);

        if (authority.equals("ROLE_MANAGER")) {
            return centerService.getMonthlyExamCounts(centerNo, year, month);
        } else {
            throw new CustomExceptions.UnauthorizedException("접근 권한이 없습니다.");
        }
    }


}
