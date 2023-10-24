package multicampussa.laams.manager.dto.examinee.response;

import lombok.Getter;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;

@Getter
public class ExamineeCompensationDetailResponse {

    private Long examineeNo;
    private String examineeName;
    private String compensationReason;
    private String imageUrl;
    private String imageReason;

    public ExamineeCompensationDetailResponse(ExamExaminee examExaminee) {
        this.examineeNo = examExaminee.getExaminee().getNo();
        this.examineeName = examExaminee.getExaminee().getName();
        this.compensationReason = examExaminee.getCompensationReason();
        this.imageUrl = examExaminee.getImageUrl();
        this.imageReason = examExaminee.getImageReason();
    }

}
