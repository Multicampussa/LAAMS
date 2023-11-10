package multicampussa.laams.manager.domain.examinee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name = "exam_examinee_no")
    private ExamExaminee examExaminee;

    private String imageUrl;

    private String imageReason;

    public void uploadImage(ExamExaminee examExaminee, String imageUrl, String imageReason) {
        this.examExaminee = examExaminee;
        this.imageUrl = imageUrl;
        this.imageReason = imageReason;
    }
}
