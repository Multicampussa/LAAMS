package multicampussa.laams.manager.controller.exam;

import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.manager.dto.exam.request.ExamCreateRequest;
import multicampussa.laams.manager.dto.exam.request.ExamUpdateRequest;
import multicampussa.laams.manager.dto.exam.response.ExamResponse;
import multicampussa.laams.manager.service.exam.ExamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExamController {

    private final ExamService examService;
    private final JwtTokenProvider jwtTokenProvider;

    public ExamController(ExamService examService, JwtTokenProvider jwtTokenProvider) {
        this.examService = examService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/manager/exam")
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
