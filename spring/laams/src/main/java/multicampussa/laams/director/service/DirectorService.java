package multicampussa.laams.director.service;

import lombok.RequiredArgsConstructor;
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
        System.out.println(directorNo);
        System.out.println(year);
        List<Exam> exams = directorRepository.findAllByDirectorNoContainingMonth(directorNo, year, month);
        for(Exam exam : exams){
            examMonthListDtos.add(new ExamMonthListDto(exam));
        }
        return examMonthListDtos;
    }

}
