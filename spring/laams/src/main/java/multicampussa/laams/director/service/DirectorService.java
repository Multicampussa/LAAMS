package multicampussa.laams.director.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.dto.ExamDayListDto;
import multicampussa.laams.director.dto.ExamMonthListDto;
import multicampussa.laams.director.repository.DirectorRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    @Transactional
    public List<ExamMonthListDto> getExamMonthList(Long directorNo, Integer year, Integer month) {
        List<ExamMonthListDto> examMonthListDtos = new ArrayList<>();
        List<Exam> exams = directorRepository.findAllByDirectorNoContainingMonth(directorNo, year, month);
        for(Exam exam : exams){
            examMonthListDtos.add(new ExamMonthListDto(exam));
        }
        return examMonthListDtos;
    }

    @Transactional
    public List<ExamDayListDto> getExamDayList(Long directorNo, Integer year, Integer month, Integer day) {
        List<ExamDayListDto> examDayListDtos = new ArrayList<>();
        List<Exam> exams = directorRepository.findAllByDirectorNoContainingDay(directorNo, year, month, day);
        for(Exam exam : exams){
            examDayListDtos.add(new ExamDayListDto(exam));
        }
        return examDayListDtos;
    }
}
