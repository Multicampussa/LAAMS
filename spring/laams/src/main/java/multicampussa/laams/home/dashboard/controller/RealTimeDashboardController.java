//package multicampussa.laams.home.dashboard.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import multicampussa.laams.home.dashboard.dto.RealTimeDashboardResDto;
//import multicampussa.laams.home.dashboard.service.RealtimeDashboardService;
//import multicampussa.laams.home.member.jwt.JwtTokenProvider;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/realtimedashboard")
//@Api(tags = "실시간 대시보드")
//public class RealTimeDashboardController {
//
//    private final RealtimeDashboardService realtimeDashboardService;
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @ApiOperation(value = "실시간 시험 진행 현황 대시보드 조회")
//    @GetMapping("/exam")
//    public ResponseEntity<Map<String, Object>> getRealTimeExamStatus(@RequestHeader String authorizaiton, ) {
//        Map<String, Object> resutlMap = new HashMap<>();
//        try{
//            String token = authorizaiton.replace("Bearer ", "");
//            String authority = jwtTokenProvider.getAuthority(token);
//
//            List<RealTimeDashboardResDto> realTimeExamList = RealtimeDashboardService.getRealTimeExamStatus(authority);
//
//            resutlMap.put("message", "실시간 시험 현황을 불러왔습니다.");
//            resutlMap.put("status", HttpStatus.OK.value());
//            resutlMap.put("data", realTimeExamList);
//            return new ResponseEntity<Map<String, Object>>(resutlMap, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            resutlMap.put("message", e.getMessage());
//            resutlMap.put("status", HttpStatus.BAD_REQUEST.value());
//            return new ResponseEntity<Map<String, Object>>(resutlMap, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//}
