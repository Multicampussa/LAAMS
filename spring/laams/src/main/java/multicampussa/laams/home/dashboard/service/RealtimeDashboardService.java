package multicampussa.laams.home.dashboard.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.dashboard.dto.RealTimeDashboardDto;
import multicampussa.laams.home.dashboard.dto.RealTimeDashboardResDto;
import multicampussa.laams.manager.domain.center.CenterRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import multicampussa.laams.manager.dto.ExamDTO;
import multicampussa.laams.manager.dto.dashboard.response.DashboardErrorReport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RealtimeDashboardService {

    public final CenterRepository centerRepository;
    public final ExamRepository examRepository;
    public final ExamExamineeRepository examExamineeRepository;

    public List<RealTimeDashboardDto> getRealTimeExamStatus(String authority) {
        // 응시자는 대시보드 조회할 수 없음(감독관, 센터관리자, 운영자 다 봐도 될 듯)
        if (authority.equals("ROLE_EXAMINEE")) {
            throw new IllegalArgumentException("대시보드 조회 권한이 없습니다.");
        }
        List<RealTimeDashboardDto> realTimeDashboards = new ArrayList<>();

        // 현재 진행중인 시험 리스트 조회
        List<ExamDTO> exams = examRepository.findOngoingExam();
        System.out.println("exams.size() = " + exams.size());
        // exams 순회하면서 쿼리 3번 호출
        for (ExamDTO exam : exams) {
            System.out.println("exam = " + exam);
            // 해당 시험 접수인원 조회
            int applicants = examExamineeRepository.getTheNumberOfApplicants(exam.getExamNo());
            // 해당 시험 응시인원 조회
            int participants = examExamineeRepository.getTheNumberOfParticipants(exam.getExamNo());
            // 해당 시험 응시율
            double attendanceRate = Math.round(participants / (applicants * 1.0) * 1000)  / 10.0;
            // 해당 시험 보상 신청수
            int compensation = examExamineeRepository.getTheNumberOfCompensation(exam.getExamNo());

            RealTimeDashboardDto realTimeDashboard = new RealTimeDashboardDto();
            realTimeDashboard.toEntity(exam, applicants, participants, attendanceRate, compensation);

            realTimeDashboards.add(realTimeDashboard);
        }

        return realTimeDashboards;


    }

}
