package multicampussa.laams.manager.service.exam;

import multicampussa.laams.director.domain.Director;
import multicampussa.laams.manager.domain.exam.ExamDirector;
import multicampussa.laams.manager.domain.exam.ExamDirectorRepository;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import multicampussa.laams.manager.domain.manager.Manager;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.domain.center.Center;
import multicampussa.laams.manager.domain.center.CenterRepository;
import multicampussa.laams.manager.domain.manager.ManagerRepository;
import multicampussa.laams.manager.dto.director.response.DirectorListResponse;
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
    private final ExamExamineeRepository examExamineeRepository;
    private final ExamDirectorRepository examDirectorRepository;

    public ExamService(ExamRepository examRepository, CenterRepository centerRepository, ManagerRepository managerRepository, ExamExamineeRepository examExamineeRepository, ExamDirectorRepository examDirectorRepository) {
        this.examRepository = examRepository;
        this.centerRepository = centerRepository;
        this.managerRepository = managerRepository;
        this.examExamineeRepository = examExamineeRepository;
        this.examDirectorRepository = examDirectorRepository;
    }

    // 시험 생성
    @Transactional
    public void saveExam(ExamCreateRequest request) {
        // 입력 받은 센터 이름으로 기존 센터 데이터를 불러와서 시험 정보 저장
        Center existingCenter = centerRepository.findByName(request.getCenterName())
                .orElseThrow(() -> new CustomExceptions.CenterNotFundException(request.getCenterName() + " 이름의 센터는 존재하지 않습니다."));
        // 입력 받은 매니저 번호로 시험 담당 매니저 호출
        Manager responsibleManager = managerRepository.findById(request.getManagerNo())
                .orElseThrow(() -> new CustomExceptions.ManagerNotFoundException(request.getManagerNo() + "번 매니저는 존재하지 않습니다."));
        examRepository.save(new Exam(existingCenter, request.getExamDate(), responsibleManager, request.getRunningTime(), request.getExamType(), request.getExamLanguage()));
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
        // 전달 받은 시험번호로 시험 조회
        Exam exam = examRepository.findById(no)
                .orElseThrow(() -> new CustomExceptions.ExamNotFoundException(no + "번 시험 없음"));
        // 시험 번호
        Long examNo = exam.getNo();

        // 시험으로 센터 번호 조회
        Long centerNo = exam.getCenter().getNo();

        // 센터 번호로 센터 조회
        Center center = centerRepository.findById(centerNo)
                .orElseThrow(() -> new CustomExceptions.CenterNotFundException(centerNo + "번 센터 없음"));

        // 시험 아이디로 응시자 전체 조회
        List<ExamExaminee> examExamineeList = examExamineeRepository.findByExamNo(examNo);
        int examineeNum = examExamineeList.size();

        // 출석한 응시자 전체 조회
        List<ExamExaminee> attendeeList = examExamineeRepository.findByAttendance(true);
        int attendanceNum = attendeeList.size();

        // 보상 대상자 전체 조회
        List<ExamExaminee> compensationList = examExamineeRepository.findByCompensation(true);
        int compensationNum = compensationList.size();

        // 시험 번호로 감독관 리스트 조회
        List<Director> directors = examDirectorRepository.findByExam(exam).stream()
                .map(ExamDirector::getDirector)
                .collect(Collectors.toList());

        // 시험 번호로 ExamDirector 조회하고 다시 DirectorListResponse 생성
        List<DirectorListResponse> directorListResponseList = examDirectorRepository.findByExam(exam).stream()
                .map(examDirector -> new DirectorListResponse(examDirector.getDirector(), examDirector))
                .collect(Collectors.toList());

        return new ExamDetailResponse(center, exam, examineeNum, attendanceNum, compensationNum, directorListResponseList);

    }

    // 시험 수정
    @Transactional
    public void updateExam(ExamUpdateRequest request) {
        // 기존 시험 찾기
        Exam existingExam = examRepository.findById(request.getExamNo())
                .orElseThrow(() -> new CustomExceptions.ExamNotFoundException(request.getExamNo() + "번 시험은 존재하지 않습니다."));
        Center existingCenter = centerRepository.findByName(request.getCenterName())
                .orElseThrow(() -> new CustomExceptions.CenterNotFundException(request.getCenterName() + " 이름의 센터는 존재하지 않습니다."));
        Manager manager = managerRepository.findById(request.getManagerNo())
                .orElseThrow(() -> new CustomExceptions.ManagerNotFoundException(request.getManagerNo() + "번 매니저는 존재하지 않습니다."));

        existingExam.updateExamInfo(existingCenter, request.getExamDate(), manager, request.getRunningTime(), request.getExamType());
    }

    // 시험 삭제
    @Transactional
    public void deleteExam(Long no) {
        Exam exam = examRepository.findById(no)
                .orElseThrow(IllegalArgumentException::new);
        examRepository.delete(exam);
    }

}
