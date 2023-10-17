package multicampussa.laams.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.director.domain.Director;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.home.member.dto.MemberDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Manager extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(unique = true)
    private String id;
    private String name;
    private String pw;
    private String phone;
    private String refreshToken;

    public static MemberDto toMemberDto(Manager manager) {
        return MemberDto.builder()
                .memberNo(manager.getNo())
                .name(manager.getName())
                .phone(manager.getPhone())
                .createdAt(manager.getCreatedAt())
                .updatedAt(manager.getUpdatedAt())
                .build();
    }

    // 리프레시 토큰 업데이트
    // 이미 리프레시 토큰이 있어도 업데이트 됨.
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
