package multicampussa.laams.manager.domain.examinee;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.manager.dto.examinee.request.ExamineeCreateRequest;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Examinee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no = null;

    @Column(nullable = false, length = 10)
    private String name;

    private int age;

    @Column(nullable = false, length = 20)
    private String phoneNum;

    @Column(nullable = false, length = 10)
    private String gender;

    private String email;

    private String id;

    private String pw;

    private int point;

    public Examinee(ExamineeCreateRequest examineeCreateRequest) {
        this.id = examineeCreateRequest.getId();
        this.pw = examineeCreateRequest.getPw();
        this.name = examineeCreateRequest.getName();
        this.age = examineeCreateRequest.getAge();
        this.phoneNum = examineeCreateRequest.getPhoneNum();
        this.gender = examineeCreateRequest.getGender();
        this.email = examineeCreateRequest.getEmail();
    }

    public void updateExamineeInfo(String name, Integer age, String phoneNum, String gender, String id, String pw) {

        this.id = id;
        this.pw = pw;
        this.name = name;
        this.age = age;
        this.phoneNum = phoneNum;
        this.gender = gender;
    }
}
