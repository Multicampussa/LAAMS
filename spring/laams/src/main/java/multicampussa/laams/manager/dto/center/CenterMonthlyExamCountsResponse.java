package multicampussa.laams.manager.dto.center;

import lombok.Getter;

@Getter
public class CenterMonthlyExamCountsResponse {

    private Long centerNo;
    private String centerName;
    private int NumberOfExam;

    public CenterMonthlyExamCountsResponse(Long centerNo, String centerName, int numberOfExam) {
        this.centerNo = centerNo;
        this.centerName = centerName;
        NumberOfExam = numberOfExam;
    }
}
