package multicampussa.laams.manager.domain.exam;

import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.director.domain.Director;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class ExamDirector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name = "exam_no")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "director_no")
    private Director director;

}

