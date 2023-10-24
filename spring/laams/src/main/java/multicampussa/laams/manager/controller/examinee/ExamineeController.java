package multicampussa.laams.manager.controller.examinee;

import multicampussa.laams.manager.dto.examinee.request.ExamineeCreateRequest;
import multicampussa.laams.manager.dto.examinee.request.ExamineeUpdateRequest;
import multicampussa.laams.manager.dto.examinee.response.ExamineeCompensationListResponse;
import multicampussa.laams.manager.dto.examinee.response.ExamineeResponse;
import multicampussa.laams.manager.service.examinee.ExamineeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExamineeController {

    private final ExamineeService examineeService;

    public ExamineeController(ExamineeService examineeService) {
        this.examineeService = examineeService;
    }

    // 응시자 생성
    @PostMapping("/manager/examinee")
    public void saveExaminee(@RequestBody ExamineeCreateRequest request) {
        examineeService.saveExaminee(request);
    }

    // 응시자 목록 조회
    @GetMapping("/manager/examinees")
    public List<ExamineeResponse> getExaminees() {
        return examineeService.getExaminees();
    }

    // 보상대상 응시자 조회
    @GetMapping("/manager/examinees/compensation")
    public List<ExamineeCompensationListResponse> getCompensationList() {
        return examineeService.getCompensationList();
    }

    // 응시자 수정
    @PutMapping("/manager/examinee")
    public void updateExaminee(@RequestBody ExamineeUpdateRequest request) {
        examineeService.updateExaminee(request);
    }

    // 응시자 삭제
    @DeleteMapping("/manager/examinee/{no}")
    public void deleteExaminee(@PathVariable Long no) {
        examineeService.deleteExaminee(no);
    }

}
