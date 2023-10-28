package multicampussa.laams.examinee.service;

import multicampussa.laams.examinee.dto.request.EnrollExamRequest;
import multicampussa.laams.examinee.dto.response.CenterExamsResponse;
import multicampussa.laams.global.CustomExceptions;
import multicampussa.laams.manager.domain.center.Center;
import multicampussa.laams.manager.domain.center.CenterRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import multicampussa.laams.manager.domain.examinee.Examinee;
import multicampussa.laams.manager.domain.examinee.ExamineeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ExamineeService {

    private final ExamRepository examRepository;
    private final ExamExamineeRepository examExamineeRepository;
    private final ExamineeRepository examineeRepository;
    private final CenterRepository centerRepository;

    public ExamineeService(ExamRepository examRepository, ExamExamineeRepository examExamineeRepository, ExamineeRepository examineeRepository, CenterRepository centerRepository) {
        this.examRepository = examRepository;
        this.examExamineeRepository = examExamineeRepository;
        this.examineeRepository = examineeRepository;
        this.centerRepository = centerRepository;
    }

    public List<CenterExamsResponse> getCenterExams(Long centerNo) {
        // 센터가 존재하는지 확인
        Center center = centerRepository.findByNo(centerNo);
        if (center == null) {
            throw new CustomExceptions.CenterNotFoundException(centerNo + "번 센터가 존재하지 않습니다.");
        }

        // 입력 받은 센터 정보로 해당 센터의 전체 시험 불러오기
        List<CenterExamsResponse> centerExamsResponses = examRepository.findByCenterNo(centerNo)
                .stream()
                .map(CenterExamsResponse::new)
                .collect(Collectors.toList());

        return centerExamsResponses;
    }

    private static final AtomicInteger counter = new AtomicInteger(00000000); // 초기 값 설정

    public String generateUniqueExamineeCode() {
        int nextValue = counter.getAndIncrement();
        if (nextValue >= 99999999) {
            // 예외 처리: 범위를 초과한 경우
            throw new RuntimeException("Examinee code range exceeded.");
        }
        return String.format("%08d", nextValue);
    }

    public void enrollExam(EnrollExamRequest enrollExamRequest) {
        // 입력 받은 시험 no로 조회
        Exam exam = examRepository.findByNo(enrollExamRequest.getExamNo());

        // 입력 받은 응시자 no로 조회
        Examinee examinee = examineeRepository.findByNo(enrollExamRequest.getExamineeNo());

        // 수험 번호 생성
        String examineeCode = generateUniqueExamineeCode();

        // 수험자 등록
        examExamineeRepository.save(new ExamExaminee(examinee, exam, examineeCode));
    }

}