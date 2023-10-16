package multicampussa.laams.director.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.home.member.dto.MemberDto;
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
    private String address;

    public void update(MemberSignUpDto memberSignUpDto, String encodedPassword) {
        this.memberName = memberSignUpDto.getName();
        this.email = memberSignUpDto.getEmail();
        this.phoneNumber = memberSignUpDto.getPhone();
        this.password = encodedPassword;
        this.isDelete = false;
        this.address = memberSignUpDto.getAddress();
    }

    public void updateVerificationCode(String email, String code) {
        this.email = email;
        this.verificationCode = code;
        this.isDelete = true;
    }

    public void updateVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public static MemberDto toAdminDto(Director director) {
        return MemberDto.builder()
                .memberId(director.getNo())
                .memberName(director.getMemberName())
                .email(director.getEmail())
                .phoneNumber(director.getPhoneNumber())
                .createdAt(director.getCreatedAt())
                .isDelete(director.getIsDelete())
                .updatedAt(director.getUpdatedAt())
                .build();
    }

    // 리프레시 토큰 업데이트
    // 이미 리프레시 토큰이 있어도 업데이트 됨.
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
