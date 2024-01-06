package multicampussa.laams.home.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.director.domain.errorReport.ErrorReport;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.home.member.dto.MemberDto;
import multicampussa.laams.home.member.dto.MemberSignUpDto;
import multicampussa.laams.home.member.dto.MemberUpdateDto;
import multicampussa.laams.home.notice.domain.Notice;
import multicampussa.laams.manager.domain.center.Center;
import multicampussa.laams.manager.domain.exam.ExamDirector;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
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
    private String role;

    @ManyToOne
    @JoinColumn(name = "center_no")
    private Center center;

    @OneToMany(mappedBy = "member")
    private List<ExamDirector> examDirectors = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ErrorReport> errorReports = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Notice> notices = new ArrayList<>();

    public void update(MemberSignUpDto memberSignUpDto, String encodedPassword) {
        this.name = memberSignUpDto.getName();
        this.email = memberSignUpDto.getEmail();
        this.phone = memberSignUpDto.getPhone();
        this.pw = encodedPassword;
        this.isDelete = false;
        this.id = memberSignUpDto.getId();
        this.role = memberSignUpDto.getRole();
    }

    public void updateVerificationCode(String email, String code) {
        this.email = email;
        this.verificationCode = code;
        this.isDelete = true;
    }

    public void updateVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public static MemberDto toMemberDto(Member member) {
        return MemberDto.builder()
                .memberNo(member.getNo())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
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

    // 비밀번호 변경
    public void updatePassword(String encode) {
        this.pw = encode;
    }

    // isDelete를 true로 변경, 리프레시 토큰 초기화
    public void delete() {
        this.isDelete = true;
        this.refreshToken = null;
    }

    // Email을 수정하고 싶을 때
    public void updateEmail(String email) {
        this.email = email;
    }
}
