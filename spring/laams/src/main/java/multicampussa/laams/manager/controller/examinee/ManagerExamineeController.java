package multicampussa.laams.manager.controller.examinee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import multicampussa.laams.manager.dto.examinee.request.ExamineeCreateRequest;
import multicampussa.laams.manager.dto.examinee.request.ExamineeUpdateRequest;
import multicampussa.laams.manager.dto.examinee.response.ExamineeCompensationDetailResponse;
import multicampussa.laams.manager.dto.examinee.response.ExamineeCompensationListResponse;
import multicampussa.laams.manager.dto.examinee.response.ExamineeResponse;
import multicampussa.laams.manager.service.examinee.ManagerExamineeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "운영자의 응시자 관련 기능")
@RestController
public class ManagerExamineeController {

    private final ManagerExamineeService examineeService;

    public ManagerExamineeController(ManagerExamineeService examineeService) {
        this.examineeService = examineeService;
    }

    // 응시자 생성
    @ApiOperation("응시자 생성")
    @PostMapping("/api/v1/manager/examinee")
    public ResponseEntity<Map<String, Object>> saveExaminee(@RequestBody ExamineeCreateRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        examineeService.saveExaminee(request);
        resultMap.put("message", "응시자가 성공적으로 생성되었습니다.");
        resultMap.put("code", HttpStatus.OK.value());
        resultMap.put("status", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    // 응시자 목록 조회
    @ApiOperation("응시자 목록 조회")
    @GetMapping("/api/v1/manager/examinees")
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
