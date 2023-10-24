package multicampussa.laams.manager.domain.examinee;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false, length = 5)
    private String gender;

    private String id;

    private String pw;

    private int point;

    public Examinee(String name, Integer age, String phoneNum, String gender, String id, String pw) {
        this.id = id;
        this.pw = pw;

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }
        this.name = name;
        this.age = age;
        this.phoneNum = phoneNum;
        this.gender = gender;
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
