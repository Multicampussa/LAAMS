package multicampussa.laams.director.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.dto.ExamListDto;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamManagerRepository;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final ExamRepository examRepository;
    private final ExamManagerRepository examManagerRepository;

    @Transactional
    public List<ExamListDto> getExamList(Long directorNo) {
        List<ExamListDto> examListDtos = new ArrayList<>();
        List<Exam> exams = examManagerRepository.findAllByDirectorNo(directorNo);

        for(Exam exam : exams){
            examListDtos.add(new ExamListDto(exam));
        }
        return examListDtos;
    }
}
