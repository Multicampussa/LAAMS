package multicampussa.laams.director.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.home.member.domain.Member;
import multicampussa.laams.manager.domain.center.Center;
import multicampussa.laams.manager.domain.exam.ExamDirector;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @OneToOne
    @JoinColumn(name = "director_no")
    private Member member;

    @OneToOne
    @JoinColumn(name = "center_no")
    private Center center;

    public enum Confirm {
        대기,
        승인,
        거절
    };

    @Enumerated(EnumType.STRING)
    private AuthRoom.Confirm confirm = AuthRoom.Confirm.대기;
}
