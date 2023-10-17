package multicampussa.laams.manager.controller.exam;

import multicampussa.laams.manager.dto.exam.request.ExamCreateRequest;
import multicampussa.laams.manager.dto.exam.request.ExamUpdateRequest;
import multicampussa.laams.manager.dto.exam.response.ExamResponse;
import multicampussa.laams.manager.dto.examinee.request.ExamineeCreateRequest;
import multicampussa.laams.manager.dto.examinee.request.ExamineeUpdateRequest;
import multicampussa.laams.manager.dto.examinee.response.ExamineeResponse;
import multicampussa.laams.manager.service.exam.ExamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/manager/exam")  //POST examinee
    public void saveExam(@RequestBody ExamCreateRequest request) {
        examService.saveExam(request);
    }

    @GetMapping("/manager/exams")
    public List<ExamResponse> getExams() {
        return examService.getExams();
    }

    @PutMapping("/manager/exam")
    public void updateExam(@RequestBody ExamUpdateRequest request) {
        examService.updateExam(request);
    }

    @DeleteMapping("/manager/exam/{no}")
    public void deleteExam(@PathVariable Long no) {
        examService.deleteExam(no);
    }
}
