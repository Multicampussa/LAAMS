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
    private Long id = null;

    // 모든 칼럼은 null false로 함

    @Column(nullable = false, length = 10)
    private String name;

    private int age;

    @Column(nullable = false, length = 20)
    private String phoneNum;

    @Column(nullable = false, length = 5)
    private String gender;

    public Examinee(String name, Integer age, String phoneNum, String gender) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }
        this.name = name;
        this.age = age;
        this.phoneNum = phoneNum;
        this.gender = gender;
    }

}
