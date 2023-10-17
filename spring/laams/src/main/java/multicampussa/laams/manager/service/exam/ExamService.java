package multicampussa.laams.manager.service.exam;

import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.dto.exam.request.ExamUpdateRequest;
import multicampussa.laams.manager.dto.exam.response.ExamResponse;
import multicampussa.laams.manager.dto.examinee.request.ExamineeCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    // 시험 생성
    public void saveExam(ExamineeCreateRequest request) {
    }

    public List<ExamResponse> getExams() {
    }

    public void updateExam(ExamUpdateRequest request) {
    }

    public void deleteExam(Long no) {
    }

}
