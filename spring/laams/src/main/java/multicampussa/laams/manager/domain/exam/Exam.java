package multicampussa.laams.manager.domain.exam;

import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.manager.domain.center.Center;
import multicampussa.laams.manager.domain.manager.Manager;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Exam extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no = null;

    @ManyToOne
    @JoinColumn(name = "center_no")
    private Center center;

    @ManyToOne
    @JoinColumn(name = "manager_no")
    private Manager manager;

    private LocalDateTime examDate;

    private int runningTime;

    private String examType;

    private String examLanguage;

    @OneToMany(mappedBy = "exam")
    private List<ExamDirector> examDirector;

    private int maxDirector = 2;

    public Exam(Center center, LocalDateTime examDate, Manager manager, int runningTime, String examType, String examLanguage) {
        this.center = center;
        this.examDate = examDate;
        this.manager = manager;
        this.runningTime = runningTime;
        this.examType = examType;
        this.examLanguage = examLanguage;
    }

    public Exam(Center existingCenter, LocalDateTime examDate, Manager responsibleManager, int runningTime, String examType, String examLanguage, int maxDirector) {
        super();
    }

    public void updateExamInfo(Center center, LocalDateTime examDate, Manager manager, int runningTime, String examType, int maxDirector) {
        this.center = center;
        this.examDate = examDate;
        this.manager = manager;
        this.runningTime = runningTime;
        this.examType = examType;
        this.maxDirector = maxDirector;
    }
}