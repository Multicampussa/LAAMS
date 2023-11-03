package multicampussa.laams.manager.service.dashboard;

import multicampussa.laams.director.domain.errorReport.ErrorReport;
import multicampussa.laams.director.repository.errorReport.ErrorReportRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamDirector;
import multicampussa.laams.manager.domain.exam.ExamDirectorRepository;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import multicampussa.laams.manager.dto.dashboard.response.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerDashboardService {

    private ErrorReportRepository errorReportRepository;
    private ExamExamineeRepository examExamineeRepository;
    private ExamDirectorRepository examDirectorRepository;
    private ExamRepository examRepository;

    public ManagerDashboardService(ErrorReportRepository errorReportRepository,
                                   ExamExamineeRepository examExamineeRepository,
                                   ExamDirectorRepository examDirectorRepository,
                                   ExamRepository examRepository) {
        this.errorReportRepository = errorReportRepository;
        this.examExamineeRepository = examExamineeRepository;
        this.examDirectorRepository = examDirectorRepository;
        this.examRepository = examRepository;
    }

    public List<DashboardResponse> generateMonthlyDashboard(int year, int month) {

        // 반환할 대쉬보드 리스트
        List<DashboardResponse> monthlyData = new ArrayList<>();

        // 입력 받은 년도, 월로 시작 날짜를 1일로 하는 startDate 생성
        LocalDate startDate = LocalDate.of(year, month, 1);

        // startDate.lengthOfMonth()로 startDate의 월의 길이를 추출하고,
        // withDayOfMonth로 해당 월의 날짜를 길이만큼 변경(마지막 날짜)
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

//            // UnprocessedAssignment 리스트 초기화
//            List<UnprocessedAssignment> unprocessedAssignments = new ArrayList<>();
//
//            // currentDate에 해당하는 ExamDirector 조회
//            List<ExamDirector> examDirectors =
//                    examDirectorRepository.findUnconfirmedByDate(sqlDate);
//
//            // examDirectors 리스트 순회하면서 unprocessedAssignments 생성
//            for (ExamDirector examDirector : examDirectors) {
//                UnprocessedAssignment unprocessedAssignment = new UnprocessedAssignment(examDirector);
//
//                // UnprocessedAssignment을 리스트에 추가
//                unprocessedAssignments.add(unprocessedAssignment);
//            }

            // UnassignedExam 리스트 초기화
            List<UnassignedExam> unassignedExams = new ArrayList<>();

            // 감독관이 한명도 배정되지 않은 시험 리스트 조회

            // currentDate에 해당하는 시험 조회
            List<Exam> todayExams = examRepository.findExamByExamDate(sqlDate);

            // exams 순회하면서 감독관 배정이 안된 시험 찾기
            // 1. 시험 번호에 해당하는 ExamDirector가 하나도 존재하지 않거나
            // 2. 존재하지만 모든 ExamDirector의 confrim이 false인 경우

            // 배정 필요한 시험 리스트 초기화
            List<Exam> assignNeededExams = new ArrayList<>();

            // 오늘 시험 전체를 순회하면서
            for (Exam exam : todayExams) {
                // 시험 번호로 ExamDirector가 존재하는지 확인한다
                List<ExamDirector> examDirectorList = examDirectorRepository.findByExamNo(exam.getNo());

                // 만약 ExamDirector 자체가 없다면 배정 필요한 시험에 추가
                if (examDirectorList == null || examDirectorList.isEmpty()) {
                    assignNeededExams.add(exam);
                } else {
                    // 그렇지 않고 ExamDirector가 있다면,
                    // 모든 examDirector.getConfrim이 false여야함
                    // 모든 confirm이 false라 가정
                    boolean allConfirmedFalse = true;
                    for (ExamDirector examDirector : examDirectorList) {
                        // 하나라도 true면 confirm이 false 가정이 틀렸다고 하고 다음 Exam 검증
                        if (examDirector.getConfirm() == true) {
                            allConfirmedFalse = false;
                            break;
                        }
                    }
                    // for문 빠져나왔는데도 여전히 true 라면
                    if (allConfirmedFalse) {
                        assignNeededExams.add(exam);
                    }
                }
            }
            // assignNeededExams 순회하면서
            for (Exam assignNeededExam : assignNeededExams) {
                // UnassignedExam 생성하고 unassignedExams에 추가
                UnassignedExam unassignedExam = new UnassignedExam(assignNeededExam);
                unassignedExams.add(unassignedExam);
            }

            // Dashboard DTO 생성 및 리스트에 추가
            DashboardResponse dashboardResponse = new DashboardResponse(
                    currentDate.getDayOfMonth(),
                    dashboardErrorReports,
                    unprocessedCompensations,
//                    unprocessedAssignments,
                    unassignedExams
            );
            monthlyData.add(dashboardResponse);

            // 다음 날짜로 이동
            currentDate = currentDate.plusDays(1);
        }

        return monthlyData;
    }
}
