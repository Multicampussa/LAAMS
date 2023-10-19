package multicampussa.laams.manager.service.exam;

import multicampussa.laams.manager.domain.manager.Manager;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.domain.exam.center.Center;
import multicampussa.laams.manager.domain.exam.center.CenterRepository;
import multicampussa.laams.manager.domain.manager.ManagerRepository;
import multicampussa.laams.manager.dto.exam.request.ExamCreateRequest;
import multicampussa.laams.manager.dto.exam.request.ExamUpdateRequest;
import multicampussa.laams.manager.dto.exam.response.ExamDetailResponse;
import multicampussa.laams.manager.dto.exam.response.ExamResponse;
import multicampussa.laams.manager.exception.CustomExceptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final CenterRepository centerRepository;
    private final ManagerRepository managerRepository;

    public ExamService(ExamRepository examRepository, CenterRepository centerRepository, ManagerRepository managerRepository) {
        this.examRepository = examRepository;
        this.centerRepository = centerRepository;
        this.managerRepository = managerRepository;
    }

    // 시험 생성
    @Transactional
    public void saveExam(ExamCreateRequest request) {
        // 입력 받은 센터 이름으로 기존 센터 데이터를 불러와서 시험 정보 저장
        Center existingCenter = centerRepository.findByName(request.getCenterName())
                .orElseThrow(IllegalArgumentException::new);
        // 입력 받은 매니저 번호로 시험 담당 매니저 호출
        Manager responsibleManager = managerRepository.findById(request.getManagerNo())
                .orElseThrow(() -> new CustomExceptions.ManagerNotFoundException(request.getManagerNo() + "번 매니저는 존재하지 않습니다."));
        examRepository.save(new Exam(existingCenter, request.getExamDate(), responsibleManager));
    }

    // 시험 목록 조회
    @Transactional(readOnly = true)
    public List<ExamResponse> getExams() {
        return examRepository.findAll().stream()
                .map(ExamResponse::new)
                .collect(Collectors.toList());
    }

    // 시험 상세 조회
    @Transactional(readOnly = true)
    public ExamDetailResponse getExam(Long no) {
        Exam exam = examRepository.findById(no)
                .orElseThrow(() -> new CustomExceptions.ExamNotFoundException(no + "번 시험 없음"));
        return new ExamDetailResponse();
    }

    // 시험 수정
    @Transactional
    public void updateExam(ExamUpdateRequest request) {
        Center existingCenter = centerRepository.findByName(request.getCenter().getName())
                .orElseThrow(IllegalArgumentException::new);
        Exam exam = examRepository.findById(request.getNo())
                .orElseThrow(IllegalArgumentException::new);
        exam.updateExamInfo(existingCenter, request.getExamDate());
    }

    // 시험 삭제
    @Transactional
    public void deleteExam(Long no) {
        Exam exam = examRepository.findById(no)
                .orElseThrow(IllegalArgumentException::new);
        examRepository.delete(exam);
    }

}
