package multicampussa.laams.centerManager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.centerManager.dto.ConfirmDirectorRequest;
import multicampussa.laams.centerManager.service.CenterManagerService;
import multicampussa.laams.global.ApiResponse;
import multicampussa.laams.global.CustomExceptions;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.manager.domain.exam.Exam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "센터매니저 관련 기능")
@RestController
@RequiredArgsConstructor
public class CenterManagerController {

    private final CenterManagerService centerManagerService;
    private final JwtTokenProvider jwtTokenProvider;

    // 센터 매니저의 감독관 신청 승인
    @ApiOperation(value = "센터 매니저의 감독관 신청 승인")
    @PutMapping("/api/v1/centermanager/confirm")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "x번 감독관의 y번 시험 승인을 완료했습니다.", response = ConfirmDirectorRequest.class),
            @io.swagger.annotations.ApiResponse(code = 400, message = "잘못된 요청"),
    })
    public ResponseEntity<ApiResponse<String>> confirmDirector(
            @RequestBody ConfirmDirectorRequest request, @RequestHeader String authorization) {
        String token = authorization.replace("Bearer", "");
        String authority = jwtTokenProvider.getAuthority(token);
        if (authority.equals("CENTER_MANAGER")) {
            centerManagerService.confirmDirector(request);
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            "success",
                            HttpStatus.OK.value(),
                            request.getDirectorNo() + "번 감독관의" + request.getExamNo() + "번 시험 승인을 완료했습니다."
                    ),
                    HttpStatus.OK);
        } else {
            throw new CustomExceptions.UnauthorizedException("접근 권한이 없습니다.");
        }

    }

}
