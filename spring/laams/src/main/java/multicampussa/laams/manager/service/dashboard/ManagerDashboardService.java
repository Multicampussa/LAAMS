package multicampussa.laams.manager.service.dashboard;

import multicampussa.laams.director.domain.errorReport.ErrorReport;
import multicampussa.laams.director.repository.errorReport.ErrorReportRepository;
import multicampussa.laams.manager.domain.exam.ExamDirector;
import multicampussa.laams.manager.domain.exam.ExamDirectorRepository;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import multicampussa.laams.manager.dto.dashboard.response.DashboardResponse;
import multicampussa.laams.manager.dto.dashboard.response.DashboardErrorReport;
import multicampussa.laams.manager.dto.dashboard.response.UnprocessedAssignment;
import multicampussa.laams.manager.dto.dashboard.response.UnprocessedCompensation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerDashboardService {

    private ErrorReportRepository errorReportRepository;
    private ExamExamineeRepository examExamineeRepository;
    private ExamDirectorRepository examDirectorRepository;

    public ManagerDashboardService(ErrorReportRepository errorReportRepository, ExamExamineeRepository examExamineeRepository, ExamDirectorRepository examDirectorRepository) {
        this.errorReportRepository = errorReportRepository;
        this.examExamineeRepository = examExamineeRepository;
        this.examDirectorRepository = examDirectorRepository;
    }

    public List<DashboardResponse> generateMonthlyDashboard(int year, int month) {

        // 반환할 대쉬보드 리스트
        List<DashboardResponse> monthlyData = new ArrayList<>();

        // 입력 받은 년도, 월로 시작 날짜를 1일로 하는 startDate 생성
        LocalDate startDate = LocalDate.of(year, month, 1);

        // startDate.lengthOfMonth()로 startDate의 월의 길이를 추출하고, withDayOfMonth로 해당 월의 날짜를 길이만큼 변경(마지막 날짜)
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // 반복문을 사용하여 날짜 범위 내의 데이터 생성
        LocalDate currentDate = startDate;

        // 현재 날짜 부터 마지막 날짜 까지
        while (!currentDate.isAfter(endDate)) {
            // 에러 리포트, 미처리 보상, 미처리 승인 데이터를 생성하고 DTO로 변환

            // DashboardErrorReport 리스트 초기화
            List<DashboardErrorReport> dashboardErrorReports = new ArrayList<>();

            // currentDate에 해당하는 ErrorReport 조회
            java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
            List<ErrorReport> errorReports = errorReportRepository.findErrorReportByDate(sqlDate);

            // errorReports 순회하며 DashboardErrorReport 생성
            for (ErrorReport errorReport : errorReports) {
                DashboardErrorReport dashboardErrorReport = new DashboardErrorReport(errorReport);
                dashboardErrorReports.add(dashboardErrorReport);
            }

            // UnprocessedCompensation 리스트 초기화
            List<UnprocessedCompensation> unprocessedCompensations = new ArrayList<>();

            // currentDate에 해당하는 ExamExaminee 조회
            // ExamExaminee에 연결된 시험 날짜가 currentDate이고 isCompensation이 false인
            List<ExamExaminee> examExaminees =
                    examExamineeRepository.findUncompensatedByDate(sqlDate);

            // examExaminees 리스트 순회하면서 UnprocessedCompensation 생성
            for (ExamExaminee examinee : examExaminees) {
                UnprocessedCompensation unprocessedCompensation =
                        new UnprocessedCompensation(examinee);

                // UnprocessedCompensation을 리스트에 추가
                unprocessedCompensations.add(unprocessedCompensation);
            }

            // UnprocessedAssignment 리스트 초기화
            List<UnprocessedAssignment> unprocessedAssignments = new ArrayList<>();

            // currentDate에 해당하는 ExamDirector 조회
            List<ExamDirector> examDirectors =
                    examDirectorRepository.findUnconfirmedByDate(sqlDate);

            // examDirectors 리스트 순회하면서 unprocessedAssignments 생성
            for (ExamDirector examDirector : examDirectors) {
                UnprocessedAssignment unprocessedAssignment = new UnprocessedAssignment(examDirector);

                // UnprocessedAssignment을 리스트에 추가
                unprocessedAssignments.add(unprocessedAssignment);
            }

            // Dashboard DTO 생성 및 리스트에 추가
            DashboardResponse dashboardResponse = new DashboardResponse(
                    currentDate.getDayOfMonth(), dashboardErrorReports, unprocessedCompensations, unprocessedAssignments
            );
            monthlyData.add(dashboardResponse);

            // 다음 날짜로 이동
            currentDate = currentDate.plusDays(1);
        }

        return monthlyData;
    }
}
