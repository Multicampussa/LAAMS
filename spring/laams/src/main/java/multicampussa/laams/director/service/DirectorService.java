package multicampussa.laams.director.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.dto.ExamInformationDto;
import multicampussa.laams.director.dto.ExamMonthDayListDto;
import multicampussa.laams.director.repository.DirectorRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final ExamRepository examRepository;

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
        Exam exam = examRepository.findById(examNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 시험은 없습니다."));

        return new ExamInformationDto(exam);
    }
}
