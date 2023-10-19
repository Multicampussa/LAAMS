package multicampussa.laams.manager.domain.exam;

import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.manager.domain.manager.Manager;
import multicampussa.laams.manager.domain.exam.center.Center;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne
    @Column(name = "manager_no")
    private Manager manager;

    public Exam(Center center, LocalDateTime examDate, Manager manager) {
        this.center = center;
        this.examDate = examDate;
        this.manager = manager;
    }

    public void updateExamInfo(Center center, LocalDateTime examDate, Manager manager) {

        this.center = center;
        this.examDate = examDate;
        this.manager = manager;
    }
}