package multicampussa.laams.manager.service.center;

import multicampussa.laams.manager.domain.center.Center;
import multicampussa.laams.manager.domain.center.CenterRepository;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.dto.center.response.CenterResponse;
import multicampussa.laams.manager.dto.director.response.CenterMonthlyExamCountsResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CenterService {

    private final ExamRepository examRepository;
    private final CenterRepository centerRepository;

    public CenterService(ExamRepository examRepository, CenterRepository centerRepository) {
        this.examRepository = examRepository;
        this.centerRepository = centerRepository;
    }

    // 전체 센터 목록 조회
    @Transactional
    public List<CenterResponse> getCenters() {
        List<Center> centers = centerRepository.findAll();

        return centers.stream()
                .map(CenterResponse::new)
                .collect(Collectors.toList());
    }

    // 센터의 달별 시험 횟수 조회
    @Transactional
    public CenterMonthlyExamCountsResponse getMonthlyExamCounts(Long centerNo, int year, int month) {
        // 특정 년도, 특정 월에 대한 시험 리스트
        int numberOfExam = examRepository.countExamsByYearAndMonth(year, month);

        // 센터 이름
        String centerName = centerRepository.findByNo(centerNo).getName();

        return new CenterMonthlyExamCountsResponse(centerNo,centerName, numberOfExam);
    }

    public Long getCenterNo(String roomName) {
        return centerRepository.findByName(roomName).get().getNo();
    }
}
