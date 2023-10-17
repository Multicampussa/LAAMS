package multicampussa.laams.director.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.home.member.dto.MemberDto;
import multicampussa.laams.home.member.dto.MemberSignUpDto;
import multicampussa.laams.home.member.dto.MemberUpdateDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Director extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_no")
    private Long no;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String id;
    private String name;
    private String pw;
    private String phone;
    private Boolean isDelete;
    private String refreshToken;
    private String verificationCode;
    private Boolean isVerified;
    private String address;

    public void update(MemberSignUpDto memberSignUpDto, String encodedPassword) {
        this.name = memberSignUpDto.getName();
        this.email = memberSignUpDto.getEmail();
        this.phone = memberSignUpDto.getPhone();
        this.pw = encodedPassword;
        this.isDelete = false;
        this.address = memberSignUpDto.getAddress();
        this.id = memberSignUpDto.getId();
    }

    public void updateVerificationCode(String email, String code) {
        this.email = email;
        this.verificationCode = code;
        this.isDelete = true;
    }

    public void updateVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public static MemberDto toMemberDto(Director director) {
        return MemberDto.builder()
                .memberNo(director.getNo())
                .name(director.getName())
                .email(director.getEmail())
                .phone(director.getPhone())
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

    // 사용자가 자신의 정보 변경
    public void update(MemberUpdateDto memberUpdateDto) {
        this.id = memberUpdateDto.getId();
        this.name = memberUpdateDto.getName();
        this.email = memberUpdateDto.getEmail();
        this.phone = memberUpdateDto.getPhone();
    }
}
