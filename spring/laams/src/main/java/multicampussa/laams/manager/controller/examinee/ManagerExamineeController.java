package multicampussa.laams.manager.controller.examinee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import multicampussa.laams.centerManager.dto.ConfirmDirectorRequest;
import multicampussa.laams.global.CustomExceptions;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.dto.examinee.request.ExamineeCompensationDenyRequest;
import multicampussa.laams.manager.dto.examinee.request.ExamineeCreateRequest;
import multicampussa.laams.manager.dto.examinee.request.ExamineeUpdateRequest;
import multicampussa.laams.manager.dto.examinee.request.ExamineeCompensationConfirmRequest;
import multicampussa.laams.manager.dto.examinee.response.ExamineeCompensationDetailResponse;
import multicampussa.laams.manager.dto.examinee.response.ExamineeCompensationListResponse;
import multicampussa.laams.manager.dto.examinee.response.ExamineeResponse;
import multicampussa.laams.manager.service.examinee.ManagerExamineeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "운영자의 응시자 관련 기능")
@RestController
public class ManagerExamineeController {

    private final ManagerExamineeService examineeService;
    private final JwtTokenProvider jwtTokenProvider;

    public ManagerExamineeController(ManagerExamineeService examineeService, JwtTokenProvider jwtTokenProvider) {
        this.examineeService = examineeService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 응시자 생성
    @ApiOperation("응시자 생성")
    @PostMapping("/api/v1/manager/examinee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "응시자가 성공적으로 생성되었습니다."),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            // 다른 응답 코드 및 메시지 정의
    })
    public ResponseEntity<Map<String, Object>> saveExaminee(@RequestBody ExamineeCreateRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        examineeService.saveExaminee(request);
        resultMap.put("message", "응시자가 성공적으로 생성되었습니다.");
        resultMap.put("code", HttpStatus.OK.value());
        resultMap.put("status", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    // 보상 승인
    @ApiOperation(value = "운영자의 보상 신청 승인")
    @PutMapping("/api/v1/manager/compensation/confirm")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(
                    code = 200, message = "x번 응시자의 y번 시험의 보상 승인을 완료했습니다."),
            @io.swagger.annotations.ApiResponse(code = 400, message = "잘못된 요청"),
    })
    public ResponseEntity<multicampussa.laams.global.ApiResponse<String>> confirmCompensation(
            @ApiIgnore @RequestHeader String authorization,
            @RequestBody ExamineeCompensationConfirmRequest request
    ) {
        String token = authorization.replace("Bearer", "");
        String authority = jwtTokenProvider.getAuthority(token);
        if (authority.equals("ROLE_MANAGER")) {
            examineeService.confirmCompensation(request);
            return new ResponseEntity<>(
                    new multicampussa.laams.global.ApiResponse<>(
                            "success",
                            HttpStatus.OK.value(),
                            request.getExamineeNo() + "번 응시자의" + request.getExamNo() + "번 시험의 보상 승인을 완료했습니다."
                    ),
                    HttpStatus.OK);
        } else {
            throw new CustomExceptions.UnauthorizedException("접근 권한이 없습니다.");
        }
    }

    // 보상 거절
    @ApiOperation(value = "운영자의 보상 신청 거절")
    @PutMapping("/api/v1/manager/compensation/deny")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "x번 응시자의 y번 시험 보상 승인을 거절했습니다."),
            @io.swagger.annotations.ApiResponse(code = 400, message = "잘못된 요청"),
    })
    public ResponseEntity<multicampussa.laams.global.ApiResponse<String>> denyCompensation(
            @ApiIgnore @RequestHeader String authorization,
            @RequestBody ExamineeCompensationDenyRequest request
    ) {
        String token = authorization.replace("Bearer", "");
        String authority = jwtTokenProvider.getAuthority(token);
        if (authority.equals("ROLE_MANAGER")) {
            examineeService.denyConfirm(request);
            return new ResponseEntity<>(
                    new multicampussa.laams.global.ApiResponse<>(
                            "success",
                            HttpStatus.OK.value(),
                            request.getExamineeNo() + "번 응시자의" + request.getExamNo() + "번 시험의 보상 거절을 완료했습니다."
                    ),
                    HttpStatus.OK);
        } else {
            throw new CustomExceptions.UnauthorizedException("접근 권한이 없습니다.");
        }
    }

    // 페이징 처리된 응시자 보상 상태 조회
    @GetMapping("/api/v1/manager/examinees/compensationStatus")
    public Page<ExamineeCompensationListResponse> getPagedExaminees(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String status) {
        return examineeService.getPagedExaminees(pageNumber, pageSize, status);
    }

    // 응시자 목록 조회
    @ApiOperation("응시자 목록 조회")
    @GetMapping("/api/v1/manager/examinees")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "응시자가 성공적으로 조회되었습니다.", response = ExamineeResponse.class),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            // 다른 응답 코드 및 메시지 정의
    })
    public List<ExamineeResponse> getExaminees() {
        return examineeService.getExaminees();
    }

    // 보상대상 응시자 조회
    @ApiOperation("보상 대상 응시자 조회")
    @GetMapping("/api/v1/manager/examinees/compensation")
    public List<ExamineeCompensationListResponse> getCompensationList() {
        return examineeService.getCompensationList();
    }

    // 보상대상자 상세 조회
    @ApiOperation("보상 대상 응시자 상세 조회")
    @GetMapping("/api/v1/manager/examinees/compensation/{examineeNo}")
    public ExamineeCompensationDetailResponse getCompensationDetail(@PathVariable Long examineeNo){
        return examineeService.getCompensationDetail(examineeNo);
    }

    // 응시자 수정
    @ApiOperation("응시자 수정")
    @PutMapping("/api/v1/manager/examinee")
    public void updateExaminee(@RequestBody ExamineeUpdateRequest request) {
        examineeService.updateExaminee(request);
    }

    // 응시자 삭제
    @ApiOperation("응시자 삭제")
    @DeleteMapping("/api/v1/manager/examinee/{no}")
    public void deleteExaminee(@PathVariable Long no) {
        examineeService.deleteExaminee(no);
    }

}
