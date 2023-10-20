package multicampussa.laams.manager.domain.center;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.manager.domain.centerManager.CenterManager;

import javax.persistence.*;
import java.io.Serializable;

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

}
