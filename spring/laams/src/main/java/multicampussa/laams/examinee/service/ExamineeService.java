package multicampussa.laams.examinee.service;

import lombok.RequiredArgsConstructor;
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

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamineeService {

    private final ExamRepository examRepository;
    private final ExamExamineeRepository examExamineeRepository;
    private final ExamineeRepository examineeRepository;
    private final CenterRepository centerRepository;

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

    public String generateUniqueExamineeCode() {
        // 기본값 설정
        String defaultCode = "00000000";

        // 저장된 ExamineeCode 중에서 가장 큰 값을 찾기
        Optional<String> maxExamineeCode = examExamineeRepository.findAll().stream()
                .map(ExamExaminee::getExamineeCode)
                .max(Comparator.naturalOrder());

        // 가장 큰 값이 존재하면 1을 더하여 새로운 코드 생성, 그렇지 않으면 기본값 반환
        return maxExamineeCode.map(code -> String.format("%08d", Integer.parseInt(code) + 1))
                .orElse(defaultCode);
    }

    public void enrollExam(EnrollExamRequest enrollExamRequest) {
        // 입력 받은 시험 no로 조회
        Exam exam = examRepository.findByNo(enrollExamRequest.getExamNo());
        if (exam == null) {
            throw new CustomExceptions.ExamNotFoundException(enrollExamRequest.getExamNo() + "번 시험이 존재하지 않습니다.");
        }

        // 입력 받은 응시자 no로 조회
        Examinee examinee = examineeRepository.findByNo(enrollExamRequest.getExamineeNo());
        if (examinee == null) {
            throw new CustomExceptions.ExamineeNotFoundException(enrollExamRequest.getExamineeNo() + "번 응시자가 존재하지 않습니다.");
        }

        // 수험 번호 생성
        String examineeCode = generateUniqueExamineeCode();

        // 수험자 등록
        examExamineeRepository.save(new ExamExaminee(examinee, exam, examineeCode));
    }

    public void updateExamineeAttendance(String authority, String examineeCode) {
        if(authority.equals("ROLE_EXAMINEE")){
            ExamExaminee examExaminee = examExamineeRepository.findByExamineeCode(examineeCode).orElse(null);
            if(examExaminee != null) {
                boolean attendacne = true;
                LocalDateTime attendanceTime = LocalDateTime.now();
                examExaminee.setAttendance(attendacne, attendanceTime);
                examExamineeRepository.save(examExaminee);
            } else {
                throw new IllegalArgumentException("없는 응시자 코드입니다.");
            }
        }else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }
}