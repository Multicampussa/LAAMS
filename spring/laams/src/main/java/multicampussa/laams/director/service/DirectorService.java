package multicampussa.laams.director.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.domain.Director;
import multicampussa.laams.director.dto.*;
import multicampussa.laams.director.repository.DirectorRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamDirector;
import multicampussa.laams.manager.domain.exam.ExamDirectorRepository;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final ExamRepository examRepository;
    private final ExamExamineeRepository examExamineeRepository;
    private final ExamDirectorRepository examDirectorRepository;

    // 감독관 시험 월별, 일별 조회
    @Transactional
    public List<ExamMonthDayListDto> getExamMonthDayList(Long directorNo, int year, int month, int day, String authority) {
        if(authority.equals("ROLE_DIRECTOR")){
            List<ExamMonthDayListDto> examMonthDayListDtos = new ArrayList<>();
            List<Exam> exams = directorRepository.findAllByDirectorNoContainingMonthAndDay(directorNo, year, month, day);
            for(Exam exam : exams){
                examMonthDayListDtos.add(new ExamMonthDayListDto(exam));
            }
            return examMonthDayListDtos;
        }
        else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    // 시험 상세정보 조회
    @Transactional
    public ExamInformationDto getExamInformation(Long examNo, String authority) {
        if(authority.equals("ROLE_DIRECTOR")){
            Optional<Exam> exam = examRepository.findById(examNo);
            if (exam.isPresent()) {
                return new ExamInformationDto(exam.get());
            } else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }
        }
        else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    // 시험 응시자 목록 조회
    @Transactional
    public List<ExamExamineeListDto> getExamExamineeList(Long examNo, String authority) {
        if(authority.equals("ROLE_DIRECTOR")){
            Optional<Exam> exam = examRepository.findById(examNo);
            if(exam.isPresent()){
                List<ExamExamineeListDto> examExamineeListDtos = new ArrayList<>();
                List<ExamExaminee> examExaminees = examExamineeRepository.findByExamNo(examNo);
                for(ExamExaminee examExaminee : examExaminees){
                    examExamineeListDtos.add(new ExamExamineeListDto(examExaminee));
                }
                return examExamineeListDtos;
            }else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }
        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    // 시험 응시자 상세 조회
    @Transactional
    public ExamExamineeDto getExamExaminee(Long examNo, Long examineeNo, String authority) {
        if(authority.equals("ROLE_DIRECTOR")){
            Optional<Exam> exam = examRepository.findById(examNo);
            if(exam.isPresent()){
                Optional<ExamExaminee> examExaminee = Optional.ofNullable(examExamineeRepository.findByExamNoAndExamineeNo(examNo, examineeNo));
                if(examExaminee.isEmpty()){
                    throw new IllegalArgumentException("해당 시험의 응시자가 아닙니다.");
                }else {
                    return new ExamExamineeDto(examExaminee.get());
                }
            } else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }
        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    // 시험 현황 조회
    @Transactional
    public ExamStatusDto getExamStatus(Long examNo, String authority) {
        if(authority.equals("ROLE_DIRECTOR")){
            Exam exam = examRepository.findById(examNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 시험은 없습니다."));

            int examineeCnt = examExamineeRepository.countByExamineeNo(examNo);
            int attendanceCnt = examExamineeRepository.countByAttendance(examNo);
//        int documentCnt = examExamineeRepository.countByDocument(examNo);

            return new ExamStatusDto(examineeCnt, attendanceCnt);
        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

    }

    // 응시자 출석 시간 업데이트 (응시자 지각여부 판단)
    @ Transactional
    public void updateAttendanceTime(Long examNo, Long examineeNo) {

        Optional<Exam> exam = examRepository.findById(examNo);
        System.out.println(examNo);
        if(exam.isPresent()){
            Optional<ExamExaminee> examExaminee = Optional.ofNullable(examExamineeRepository.findByExamNoAndExamineeNo(examNo, examineeNo));
            if(examExaminee.isEmpty()){
                throw new IllegalArgumentException("해당 시험의 응시자가 아닙니다.");
            }else {
                // 출석 시간 업데이트
                examExaminee.get().updateAttendanceTime(LocalDateTime.now());
                examExamineeRepository.save(examExaminee.get());
            }
        } else {
            throw new IllegalArgumentException("해당 시험은 없습니다.");
        }
    }

    // 응시자 출석 확인
    @Transactional
    public CheckAttendanceDto checkAttendance(Long examNo, Long examineeNo, String authority) {
        if(authority.equals("ROLE_DIRECTOR")){
            Optional<Exam> exam = examRepository.findById(examNo);
            if(exam.isPresent()){
                Optional<ExamExaminee> examExaminee = Optional.ofNullable(examExamineeRepository.findByExamNoAndExamineeNo(examNo, examineeNo));
                if(examExaminee.isEmpty()){
                    throw new IllegalArgumentException("해당 시험의 응시자가 아닙니다.");
                }else {
                    // 응시자의 출석 시간과 시험 시작 시간 비교
                    LocalDateTime examineeAttendanceTime =examExaminee.get().getAttendanceTime();
                    LocalDateTime examStartTime = exam.get().getExamDate();
                    // 응시자의 출석 시간이 더 빠름(지각x)
                    if(examineeAttendanceTime.isBefore(examStartTime)){
                        Boolean attendance = true;
                        Boolean compensation = false;
                        String compensationType = null;

                        CheckAttendanceDto checkAttendanceDto = new CheckAttendanceDto(attendance, compensation, compensationType);
                        examExaminee.get().updateAttendace(checkAttendanceDto);
                        return checkAttendanceDto;
                    }else{
                        Boolean attendance = true;
                        Boolean compensation = true;
                        String compensationType = "지각";

                        CheckAttendanceDto checkAttendanceDto = new CheckAttendanceDto(attendance, compensation, compensationType);
                        examExaminee.get().updateAttendace(checkAttendanceDto);
                        return checkAttendanceDto;
                    }
                }
            } else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }
        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

    }

    // 감독관 시험 배정 요청
    // 요청 한 번 했으면 더 안되게 만들어야함
    @Transactional
    public void requestExamAssignment(Long examNo, String authority, String directorId) {
        System.out.println(directorId);
        if(authority.equals("ROLE_DIRECTOR")){
            Exam exam = examRepository.findById(examNo).orElse(null);
            Director director = directorRepository.findById(directorId);
            // 시험 있으면 배정 요청(
            if(exam != null){
                ExamDirector examDirector = new ExamDirector();
                examDirector.setExam(exam, director);
                examDirectorRepository.save(examDirector);
            }
            else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }

        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }


}
