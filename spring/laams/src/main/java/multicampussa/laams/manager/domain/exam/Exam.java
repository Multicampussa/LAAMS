package multicampussa.laams.manager.domain.exam;

import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.manager.domain.exam.center.Center;
import multicampussa.laams.manager.domain.examinee.Examinee;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Exam extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no = null;

    // 모든 칼럼은 null false

    @ManyToOne
    @JoinColumn(name = "center_no")
    private Center center;

    @Column
    private LocalDateTime examDate;

}