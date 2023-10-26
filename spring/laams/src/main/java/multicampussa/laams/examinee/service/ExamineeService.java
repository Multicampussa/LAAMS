package multicampussa.laams.examinee.service;

import multicampussa.laams.examinee.dto.response.CenterExamsResponse;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamineeService {

    private final ExamRepository examRepository;

    public ExamineeService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public List<CenterExamsResponse> getCenterExams(Long centerNo) {
        // 입력 받은 센터 정보로 해당 센터의 전체 시험 불러오기
        List<CenterExamsResponse> centerExamsResponses = examRepository.findByCenterNo(centerNo).stream()
                .map(CenterExamsResponse::new)
                .collect(Collectors.toList());

        return centerExamsResponses;
    }
}
