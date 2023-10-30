package multicampussa.laams.director.controller.errorReport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.dto.errorReport.ErrorReportCreateDto;
import multicampussa.laams.director.service.DirectorService;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/director")
@Api(tags = "감독관 에러리포트")
public class DirectorErrorReportController {

    private final JwtTokenProvider jwtTokenProvider;
    private final DirectorService directorService;

    @ApiOperation(value = "에러리포트 작성")
    @PostMapping("/errorReport")
    public ResponseEntity<Map<String, Object>> createErrorReport(@RequestHeader String authorization, @RequestBody ErrorReportCreateDto errorReportCreateDto){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String token  = authorization.replace("Bearer ", "");
            String authority = jwtTokenProvider.getAuthority(token);
            Long directorNo = jwtTokenProvider.getMemberNo(token);

            directorService.createErrorReport(errorReportCreateDto, authority, directorNo);
            resultMap.put("message", "에러리포트를 작성하였습니다.");
            resultMap.put("code", HttpStatus.OK.value());
            resultMap.put("status", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

}
