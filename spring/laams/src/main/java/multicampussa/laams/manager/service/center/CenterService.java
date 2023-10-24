package multicampussa.laams.manager.service.center;

import multicampussa.laams.manager.domain.center.CenterRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.dto.center.CenterMonthlyExamCountsResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CenterService {

    private final ExamRepository examRepository;
    private final CenterRepository centerRepository;

    public CenterService(ExamRepository examRepository, CenterRepository centerRepository) {
        this.examRepository = examRepository;
        this.centerRepository = centerRepository;
    }

    // 센터의 달별 시험 횟수 조회
    @Transactional
    public CenterMonthlyExamCountsResponse getMonthlyExamCounts(Long centerNo, int year, int month) {
        // 특정 년도, 특정 월에 대한 시험 리스트
        List<Exam> exams = examRepository.findExamsByYearAndMonth(year, month);
        int numberOfExam = exams.size();

        // 센터 이름
        String centerName = centerRepository.findByNo(centerNo).getName();

        return new CenterMonthlyExamCountsResponse(centerNo,centerName, numberOfExam);
    }
}
