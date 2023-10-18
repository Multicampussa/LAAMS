package multicampussa.laams.manager.domain.examinee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamManager;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ExamExaminee extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examinee_no")
    private Examinee examinee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_manager_no")
    private ExamManager examManager;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_no")
    private Exam exam;

    @Column(unique = true)
    private String examineeCode;
    private Boolean attendance;
    private Boolean document;
    private Double percent;

}