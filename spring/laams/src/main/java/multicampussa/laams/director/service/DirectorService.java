package multicampussa.laams.director.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.dto.*;
import multicampussa.laams.director.repository.DirectorRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final ExamRepository examRepository;
    private final ExamExamineeRepository examExamineeRepository;

    // 감독관 시험 월별, 일별 조회
    @Transactional
    public List<ExamMonthDayListDto> getExamMonthDayList(Long directorNo, int year, int month, int day) {
        List<ExamMonthDayListDto> examMonthDayListDtos = new ArrayList<>();
        List<Exam> exams = directorRepository.findAllByDirectorNoContainingMonthAndDay(directorNo, year, month, day);
        for(Exam exam : exams){
            examMonthDayListDtos.add(new ExamMonthDayListDto(exam));
        }
        return examMonthDayListDtos;
    }

    // 시험 상세정보 조회
    @Transactional
    public ExamInformationDto getExamInformation(Long examNo) {
        Optional<Exam> exam = examRepository.findById(examNo);
        if (exam.isPresent()) {
            return new ExamInformationDto(exam.get());
        } else {
            throw new IllegalArgumentException("해당 시험은 없습니다.");
        }
    }

    // 시험 응시자 목록 조회
    @Transactional
    public List<ExamExamineeListDto> getExamExamineeList(Long examNo) {
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

    // 시험 응시자 상세 조회
    @Transactional
    public ExamExamineeDto getExamExaminee(Long examNo, Long examineeNo) {
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

    // 시험 현황 조회
    @Transactional
    public ExamStatusDto getExamStatus(Long examNo) {
        Exam exam = examRepository.findById(examNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 시험은 없습니다."));

        int examineeCnt = examExamineeRepository.countByExamineeNo(examNo);
        int attendanceCnt = examExamineeRepository.countByAttendance(examNo);
        int documentCnt = examExamineeRepository.countByDocument(examNo);

        return new ExamStatusDto(examineeCnt, attendanceCnt, documentCnt);

    }
}
