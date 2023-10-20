package multicampussa.laams.manager.dto.exam.response;

import lombok.Getter;
import multicampussa.laams.manager.domain.center.Center;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.dto.director.response.DirectorListResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ExamDetailResponse {

    private String centerName;  // 센터명
    private String centerManagerName;  // 센터 매니저 이름
    private String examType;  // 시험 타입
    private LocalDateTime examDate;  // 시험 날짜
    private int examineeNum;  // 총 응시자 수
    private int attendanceNum; // 출석자 수
    private int compensationNum;  // 보상 대상자 수
    private List<DirectorListResponse> directors;  // 시험 담당 감독관 리스트

    public ExamDetailResponse(Center center, Exam exam, int examineeNum, int attendanceNum, int compensationNum, List<DirectorListResponse> directors) {
        this.centerName = center.getName();
        this.centerManagerName = center.getCenterManager().getName();
        this.examType = exam.getExamType();
        this.examDate = exam.getExamDate();
        this.examineeNum = examineeNum;
        this.attendanceNum = attendanceNum;
        this.compensationNum = compensationNum;
        this.directors = directors;
    }
}
