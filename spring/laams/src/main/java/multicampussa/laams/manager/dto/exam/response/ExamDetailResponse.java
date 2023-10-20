package multicampussa.laams.manager.dto.exam.response;

import lombok.Getter;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.dto.director.DirectorListResponse;

import java.util.List;

@Getter
public class ExamDetailResponse {

    private String centerName;  // 센터명
    private String centerManagerName;  // 센터 매니저 이름
    private String examType;  // 시험 타입
    private String examDate;  // 시험 날짜
    private int examineeNum;  // 총 응시자 수
    private int attendanceNum; // 출석자 수
    private int compensationNum;  // 보상 대상자 수
    private List<DirectorListResponse> directors;
}
