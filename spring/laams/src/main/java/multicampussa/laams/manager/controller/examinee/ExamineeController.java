package multicampussa.laams.manager.controller.examinee;

import multicampussa.laams.manager.dto.examinee.request.ExamineeCreateRequest;
import multicampussa.laams.manager.dto.examinee.request.ExamineeUpdateRequest;
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

    @PostMapping("/manager/examinee")  //POST examinee
    public void saveExaminee(@RequestBody ExamineeCreateRequest request) {
        examineeService.saveExaminee(request);
    }

    @GetMapping("/manager/examinees")
    public List<ExamineeResponse> getExaminees() {
        return examineeService.getExaminees();
    }

    @PutMapping("/manager/examinee")
    public void updateExaminee(@RequestBody ExamineeUpdateRequest request) {
        examineeService.updateExaminee(request);
    }

    @DeleteMapping("/manager/examinee/{no}")
    public void deleteExaminee(@PathVariable Long no) {
        examineeService.deleteExaminee(no);
    }
}
