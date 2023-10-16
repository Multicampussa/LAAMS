package multicampussa.laams.director.enitty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.home.member.dto.MemberSignUpDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Director extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private Long no;

    @Column(unique = true)
    private String email;
    private String memberName;
    private String password;
    private String phoneNumber;
    private Boolean isDelete;
    private String refreshToken;
    private String verificationCode;
    private Boolean isVerified;

    public void update(MemberSignUpDto memberSignUpDto, String encodedPassword) {
        this.memberName = memberSignUpDto.getMemberName();
        this.email = memberSignUpDto.getEmail();
        this.phoneNumber = memberSignUpDto.getPhoneNumber();
        this.password = encodedPassword;
        this.isDelete = false;
    }
}
