package multicampussa.laams.centerManager.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.centerManager.domain.CenterManager;
import multicampussa.laams.centerManager.domain.CenterManagerRepository;
import multicampussa.laams.centerManager.dto.CenterExamListDto;
import multicampussa.laams.centerManager.dto.ConfirmDirectorRequest;
import multicampussa.laams.director.dto.director.ExamMonthDayListDto;
import multicampussa.laams.global.CustomExceptions;
import multicampussa.laams.manager.domain.center.CenterRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamDirector;
import multicampussa.laams.manager.domain.exam.ExamDirectorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterManagerService {

    private final ExamDirectorRepository examDirectorRepository;
    private final CenterManagerRepository centerManagerRepository;
    private final CenterRepository centerRepository;

    @Transactional
    public void confirmDirector(ConfirmDirectorRequest request) {
        ExamDirector examDirector =
                examDirectorRepository.findByExamNoAndDirectorNo(request.getExamNo(), request.getDirectorNo());
        if (examDirector == null) {
            throw new CustomExceptions.ExamDirectorNotFoundException("해당 시험을 신청한 감독관이 없습니다.");
        }
        examDirector.confirmDirector();
    }

    @Transactional
    public List<CenterExamListDto> getCenterExamList(String centerManagerId, int year, int month, int day, String authority){
        if(authority.equals("ROLE_CENTER_MANAGER")){
            List<CenterExamListDto> centerExamListDtos = new ArrayList<>();
            List<Exam> exams = centerRepository.findAllByCenterManagerIdContainingMonthAndDay(centerManagerId, year, month, day);
            for(Exam exam : exams){
                centerExamListDtos.add(new CenterExamListDto(exam));
            }
            return centerExamListDtos;
        }
        else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }
}
