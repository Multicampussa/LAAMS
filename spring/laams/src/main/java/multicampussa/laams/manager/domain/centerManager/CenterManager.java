package multicampussa.laams.manager.domain.centerManager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.home.member.dto.MemberDto;
import multicampussa.laams.home.member.dto.MemberUpdateDto;
import multicampussa.laams.manager.domain.center.Center;
import multicampussa.laams.manager.domain.manager.Manager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class CenterManager extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no = null;

    private String name;

    private String id;

    private String pw;

    private String phoneNum;

    private String email;

    private String code;

    private String refreshToken;

    public static MemberDto toMemberDto(CenterManager centerManager) {
        return MemberDto.builder()
                .memberNo(centerManager.getNo())
                .name(centerManager.getName())
                .createdAt(centerManager.getCreatedAt())
                .phone(centerManager.getPhoneNum())
                .updatedAt(centerManager.getUpdatedAt())
                .email(centerManager.getEmail())
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
        this.phoneNum = memberUpdateDto.getPhone();
        this.email = memberUpdateDto.getEmail();
    }

    public void updatePassword(String encode) {
        this.pw = encode;
    }
}

