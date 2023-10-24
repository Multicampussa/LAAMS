package multicampussa.laams.manager.dto.examinee.response;

import lombok.Getter;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamDirector;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;

import java.time.LocalDateTime;

@Getter
public class ExamineeCompensationListResponse {

    private Long examNo;
    private String examType;
    private LocalDateTime examDate;
    private String examineeName;
    private String examineeCode;
    private String compensationReason;

    public ExamineeCompensationListResponse(ExamExaminee examExaminee) {
        this.examNo = examExaminee.getExam().getNo();
        this.examType = examExaminee.getExam().getExamType();
        this.examDate = examExaminee.getExam().getExamDate();
        this.examineeName = examExaminee.getExaminee().getName();
        this.examineeCode = examExaminee.getExamineeCode();
        this.compensationReason = examExaminee.getCompensationReason();
    }
}
