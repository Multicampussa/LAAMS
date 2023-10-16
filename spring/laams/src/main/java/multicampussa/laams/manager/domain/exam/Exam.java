package multicampussa.laams.manager.domain.exam;

import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.manager.domain.exam.center.Center;
import multicampussa.laams.manager.domain.examinee.Examinee;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no = null;

    // 모든 칼럼은 null false

    @ManyToOne
    private Center center;

    @Temporal(TemporalType.DATE)
    private Date examDate;

    @Temporal(TemporalType.TIME)
    private Date examTime;

}

