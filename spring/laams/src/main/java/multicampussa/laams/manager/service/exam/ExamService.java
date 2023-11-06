package multicampussa.laams.manager.service.exam;

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
import multicampussa.laams.global.CustomExceptions;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> saveExam(ExamCreateRequest request) {
        // 입력 받은 센터 이름으로 기존 센터 데이터를 불러와서 시험 정보 저장
        Center existingCenter = centerRepository.findByName(request.getCenterName())
                .orElseThrow(() -> new CustomExceptions.CenterNotFoundException(request.getCenterName() + " 이름의 센터는 존재하지 않습니다."));
        // 입력 받은 매니저 번호로 시험 담당 매니저 호출
        Manager responsibleManager = managerRepository.findById(request.getManagerNo())
                .orElseThrow(() -> new CustomExceptions.ManagerNotFoundException(request.getManagerNo() + "번 매니저는 존재하지 않습니다."));
        examRepository.save(new Exam(existingCenter, request.getExamDate(), responsibleManager,
                request.getRunningTime(), request.getExamType(), request.getExamLanguage(), request.getMaxDirector()));

        return ResponseEntity.ok("시험이 성공적으로 생성되었습니다");
    }


    // 시험 목록 조회
    @Transactional(readOnly = true)
    public List<ExamResponse> getExams() {
        List<Exam> exams = examRepository.findAll();
        if (exams.isEmpty()) {
            throw new CustomExceptions.ExamNotFoundException("시험 목록이 없습니다. 시험을 생성하세요.");
        }

        return exams.stream()
                .map(ExamResponse::new)
                .collect(Collectors.toList());
    }

    // 시험 상세 조회
    @Transactional(readOnly = true)
    public ExamDetailResponse getExam(Long no) {
        // 전달 받은 시험번호로 시험 조회
        Exam exam = examRepository.findByNo(no);

        // 시험 번호
        Long examNo = exam.getNo();

        // 시험으로 센터 번호 조회
        Long centerNo = exam.getCenter().getNo();

        // 센터 번호로 센터 조회
        Center center = centerRepository.findByNo(centerNo);

        // 시험 아이디로 응시자 전체 조회
        List<ExamExaminee> examExamineeList = examExamineeRepository.findByExamNo(examNo);
        int examineeNum = examExamineeList.size();

        // 시험에 출석한 응시자 전체 조회
        List<ExamExaminee> attendeeList = examExamineeRepository.findByExamNoAndAttendance(examNo, true);
        int attendanceNum = attendeeList.size();

        // 시험의 보상 대상자 전체 조회
        List<ExamExaminee> compensationList = examExamineeRepository.findByExamNoAndCompensation(examNo, true);
        int compensationNum = compensationList.size();

        // 시험 번호로 ExamDirector 조회하고 다시 DirectorListResponse 생성
        List<DirectorListResponse> directorListResponseList = examDirectorRepository.findByExam(exam).stream()
                .map(examDirector -> new DirectorListResponse(examDirector.getDirector(), examDirector))
                .collect(Collectors.toList());

        return new ExamDetailResponse(center, exam, examineeNum, attendanceNum, compensationNum, directorListResponseList);

    }

    // 월별 시험 조회
    @Transactional(readOnly = true)
    public List<ExamResponse> getMonthlyExams(Integer year, Integer month) {
        // 특정 년도, 특정 월 시험 조회
        List<Exam> monthlyExams = examRepository.findExamsByYearAndMonth(year, month);

        return monthlyExams.stream()
                .map(ExamResponse::new)
                .collect(Collectors.toList());
    }

    // 일별 시험 조회
    @Transactional(readOnly = true)
    public List<ExamResponse> getDailyExams(Integer year, Integer month, Integer day) {
        // 특정 년도, 특정 월, 특정 일 시험 조회
        List<Exam> dailyExams = examRepository.findExamsByYearMonthAndDay(year, month, day);

        return dailyExams.stream()
                .map(ExamResponse::new)
                .collect(Collectors.toList());
    }

    // 시험 수정
    @Transactional
    public void updateExam(Long examNo, ExamUpdateRequest request) {
        Exam existingExam = examRepository.findById(examNo)
                .orElseThrow(() -> new CustomExceptions.ExamNotFoundException(examNo + "번 시험은 존재하지 않습니다."));
        Center existingCenter = centerRepository.findByName(request.getNewCenterName())
                .orElseThrow(() -> new CustomExceptions.CenterNotFoundException(request.getNewCenterName() + " 이름의 센터는 존재하지 않습니다."));
        Manager manager = managerRepository.findById(request.getNewManagerNo())
                .orElseThrow(() -> new CustomExceptions.ManagerNotFoundException(request.getNewManagerNo() + "번 매니저는 존재하지 않습니다."));

        existingExam.updateExamInfo(existingCenter, request.getNewExamDate(), manager, request.getNewRunningTime(),
                request.getNewExamType(), request.getNewMaxDirector());
    }

    // 시험 삭제
    @Transactional
    public void deleteExam(Long no) {
        Exam exam = examRepository.findById(no)
                .orElseThrow(() -> new CustomExceptions.ExamNotFoundException(no + "번 시험은 존재하지 않습니다."));
        examRepository.delete(exam);
    }

}
