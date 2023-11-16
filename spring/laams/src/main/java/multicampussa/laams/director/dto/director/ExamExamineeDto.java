package multicampussa.laams.director.dto.director;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamExamineeDto {

    private Long examineeNo;
    private String name;
    private String phoneNum;
    private String gender;
    private String email;

    public ExamExamineeDto(ExamExaminee examExaminee){
        this.examineeNo = examExaminee.getExaminee().getNo();
        this.name = examExaminee.getExaminee().getName();
        this.phoneNum = examExaminee.getExaminee().getPhoneNum();
        this.gender = examExaminee.getExaminee().getGender();
        this.email = examExaminee.getExaminee().getEmail();
    }
}
