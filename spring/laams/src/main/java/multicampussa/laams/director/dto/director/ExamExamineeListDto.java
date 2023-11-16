package multicampussa.laams.director.dto.director;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamExamineeListDto {

    private Long examineeNo; // 응시자 pk
    private String examineeName;
    private String examineeCode;
    private Boolean attendance;
    private ExamExaminee.DocumentStatus document;
    private LocalDateTime attendanceTime;

    public ExamExamineeListDto(ExamExaminee examExaminee){
        this.examineeNo = examExaminee.getExaminee().getNo();
        this.examineeName = examExaminee.getExaminee().getName();
        this.examineeCode = examExaminee.getExamineeCode();
        this.attendance = examExaminee.getAttendance();
        this.document = examExaminee.getDocument();
        this.attendanceTime = examExaminee.getAttendanceTime();
    }
}
