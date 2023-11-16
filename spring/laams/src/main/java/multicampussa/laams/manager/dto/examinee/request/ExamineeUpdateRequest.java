package multicampussa.laams.manager.dto.examinee.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.manager.domain.examinee.Examinee;

@Getter
public class ExamineeUpdateRequest {

    private Long no;
    private String name;
    private int age;
    private String phoneNum;
    private String gender;
    private String id;
    private String pw;

}
