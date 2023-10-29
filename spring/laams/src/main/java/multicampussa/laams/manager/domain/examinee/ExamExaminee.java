package multicampussa.laams.manager.domain.examinee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.director.dto.CheckAttendanceDto;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.manager.domain.exam.Exam;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_no")
    private Exam exam;

    @Column(unique = true)
    private String examineeCode;

    private Boolean attendance = false;

    public enum DocumentStatus {
        서류_제출_대기,
        서류_제출_완료,
        서류_미제출
    };

    @Enumerated(EnumType.STRING)
    private DocumentStatus document = DocumentStatus.서류_제출_대기;

    private Boolean compensation = false;

    private String compensationType;

    private String compensationReason;

    private String imageUrl;

    private String imageReason;

    private LocalDateTime attendanceTime = null;


    public void updateAttendanceTime(LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public ExamExaminee(Examinee examinee, Exam exam, String examineeCode) {
        this.examinee = examinee;
        this.exam = exam;
        this.examineeCode = examineeCode;

    }

    // 출결 확인
    public void updateAttendace(CheckAttendanceDto checkAttendanceDto) {
        this.attendance = checkAttendanceDto.getAttendance();
        this.compensation = checkAttendanceDto.getCompensation();
        this.compensationType = checkAttendanceDto.getCompensationType();
    }


}
