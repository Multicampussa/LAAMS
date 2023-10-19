package multicampussa.laams.manager.domain.exam;

import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.director.domain.Director;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.manager.domain.manager.Manager;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ExamManager extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_no")
    private Manager manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_no")
    private Director director;

}
