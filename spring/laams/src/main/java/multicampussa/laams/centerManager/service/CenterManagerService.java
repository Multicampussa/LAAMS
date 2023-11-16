package multicampussa.laams.centerManager.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.centerManager.domain.CenterManager;
import multicampussa.laams.centerManager.domain.CenterManagerRepository;
import multicampussa.laams.centerManager.dto.CenterExamDto;
import multicampussa.laams.centerManager.dto.CenterExamListDto;
import multicampussa.laams.centerManager.dto.ConfirmDirectorRequest;
import multicampussa.laams.centerManager.dto.DirectorAssignmentRequestListResponse;
import multicampussa.laams.director.dto.director.ExamMonthDayListDto;
import multicampussa.laams.global.CustomExceptions;
import multicampussa.laams.manager.domain.center.Center;
import multicampussa.laams.manager.domain.center.CenterRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamDirector;
import multicampussa.laams.manager.domain.exam.ExamDirectorRepository;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterManagerService {

    private final ExamDirectorRepository examDirectorRepository;
    private final ExamRepository examRepository;
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
    public void denyDirector(ConfirmDirectorRequest request) {
        ExamDirector examDirector =
                examDirectorRepository.findByExamNoAndDirectorNo(request.getExamNo(), request.getDirectorNo());
        if (examDirector == null) {
            throw new CustomExceptions.ExamDirectorNotFoundException("해당 시험을 신청한 감독관이 없습니다.");
        }
        examDirector.denyDirector();
    }

    @Transactional
    public List<DirectorAssignmentRequestListResponse> getDirectorAssignmentRequestList(Long examNo) {
        List<ExamDirector> examDirectors = examDirectorRepository.findUnconfirmed(examNo);
        if (examDirectors == null) {
            throw new CustomExceptions.ExamDirectorNotFoundException("해당 시험을 신청한 감독관이 없습니다.");
        }
        System.out.println(examDirectors);
        List<DirectorAssignmentRequestListResponse> responses = new ArrayList<>();
        for (ExamDirector examDirector : examDirectors) {
            responses.add(new DirectorAssignmentRequestListResponse(examDirector));
        }
        return responses;
    }


    @Transactional
    public List<CenterExamListDto> getCenterExamList(String centerManagerId, int year, int month, int day, String authority){
        if(authority.equals("ROLE_CENTER_MANAGER")){
            List<CenterExamListDto> centerExamListDtos = new ArrayList<>();
            List<Exam> exams = centerRepository.findAllByCenterManagerIdContainingMonthAndDay(centerManagerId, year, month, day);
            for(Exam exam : exams){
                int cntConfirmDirector = examDirectorRepository.countByConfirm(exam.getNo());
                centerExamListDtos.add(new CenterExamListDto(exam, cntConfirmDirector));
            }
            return centerExamListDtos;
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    @Transactional
    public List<CenterExamDto> getCenterExam(String centerManagerId, Long examNo, String authority) {
        if (authority.equals("ROLE_CENTER_MANAGER")) {
            List<CenterExamDto> centerExamDtos = new ArrayList<>();
            Exam exam = examRepository.findByNo(examNo);
            if (exam != null) {
                boolean isCenterManagerExists = exam.getCenter().getCenterManager().getId().equals(centerManagerId);
                if (isCenterManagerExists) {
                    List<ExamDirector> examDirectors = exam.getExamDirector();
                    for (ExamDirector examDirector : examDirectors) {
                        centerExamDtos.add(new CenterExamDto(examDirector));
                    }
                    return centerExamDtos;
                } else {
                    throw new IllegalArgumentException("조회할 권한이 없습니다.");
                }
            } else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }
}
