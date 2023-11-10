package multicampussa.laams.manager.dto.examinee.response;

import lombok.Getter;
import multicampussa.laams.manager.domain.examinee.Examinee;

@Getter
public class ExamineeResponse {

    private long no;
    private String name;
    private int age;
    private String gender;
    private String phoneNum;

    public ExamineeResponse(Examinee examinee) {
        this.no = examinee.getNo();
        this.name = examinee.getName();
        this.age = examinee.getAge();
        this.gender = examinee.getGender();
        this.phoneNum = examinee.getPhoneNum();
    }
}
