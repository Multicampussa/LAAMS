package multicampussa.laams.director.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.dto.ExamListDto;
import multicampussa.laams.director.repository.DirectorRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamManager;
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
    private final DirectorRepository directorRepository;
    private final ExamManagerRepository examManagerRepository;

    @Transactional
    public List<ExamListDto> getExamList(Long directorNo) {
//        List<Exam> exams = examRepository.findAllByDirectorNo(directorNo);
//        List<Exam> exams = examRepository.findAll();
        List<ExamListDto> examListDtos = new ArrayList<>();
        List<ExamManager> examManagers = examManagerRepository.findAllByDirectorNo(directorNo);
        for(ExamManager examManger : examManagers){
            examListDtos.add(new ExamListDto(examManger));
        }
        return examListDtos;
//        List<ExamListDto> examListDtos = new ArrayList<>();

    }
}
