package multicampussa.laams.centerManager.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.centerManager.dto.ConfirmDirectorRequest;
import multicampussa.laams.global.CustomExceptions;
import multicampussa.laams.manager.domain.exam.ExamDirector;
import multicampussa.laams.manager.domain.exam.ExamDirectorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CenterManagerService {

    private final ExamDirectorRepository examDirectorRepository;

    @Transactional
    public void confirmDirector(ConfirmDirectorRequest request) {
        ExamDirector examDirector =
                examDirectorRepository.findByExamNoAndDirectorNo(request.getExamNo(), request.getDirectorNo());
        if (examDirector == null) {
            throw new CustomExceptions.ExamDirectorNotFoundException("해당 시험을 신청한 감독관이 없습니다.");
        }
        examDirector.confirmDirector();
    }
}
