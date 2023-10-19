package multicampussa.laams.manager.domain.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.manager.domain.exam.center.Center;
import multicampussa.laams.manager.domain.examinee.Examinee;
import org.springframework.format.annotation.DateTimeFormat;

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

    public Exam(Center center, LocalDateTime examDate) {
        this.center = center;
        this.examDate = examDate;
    }

    public void updateExamInfo(Center center, LocalDateTime examDate) {

        this.center = center;
        this.examDate = examDate;
    }
}