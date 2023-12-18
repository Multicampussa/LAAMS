package multicampussa.laams.manager.domain.center;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.centerManager.domain.CenterManager;
import multicampussa.laams.home.member.domain.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Center extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no = null;

    @OneToOne
    @JoinColumn(name = "center_manager_no")
    private CenterManager centerManager;

    private String name;

    private Double latitude;

    private Double longitude;

    @Column(length = 200)
    private String region;

    @OneToMany(mappedBy = "center")
    private List<Member> member = new ArrayList<>();
}
