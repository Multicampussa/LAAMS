package multicampussa.laams.centerManager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.centerManager.service.CenterManagerService;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.manager.domain.exam.Exam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "센터매니저 관련 기능")
@RestController
@RequiredArgsConstructor
public class CenterManagerController {

    private final CenterManagerService centerManagerService;
    private final JwtTokenProvider jwtTokenProvider;



}
