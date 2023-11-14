package multicampussa.laams.home.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import multicampussa.laams.home.notice.domain.Notice;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.dto.ExamDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealTimeDashboardDto {
    // 시험장 지역
    private String region;
    // 시험장 이름
    private String center;
    // 시험 key값
    private Long exam;

    // 접수 인원
    private int applicants;
    // 시험 응시 인원
    private int participants;
    // 응시율
    private double attendanceRate;

    // 보상 신청수
    private int compensation;

    public void toEntity(ExamDTO exam, int applicants, int participants, double attendanceRate, int compensation){
        this.exam = exam.getExamNo();
        this.region = exam.getRegion();
        this.center = exam.getCenter();
        this.applicants = applicants;
        this.participants = participants;
        this.attendanceRate = attendanceRate;
        this.compensation = compensation;
    }


}
