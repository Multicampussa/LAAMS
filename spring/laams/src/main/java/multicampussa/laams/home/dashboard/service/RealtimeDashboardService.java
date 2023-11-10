//package multicampussa.laams.home.dashboard.service;
//
//import lombok.RequiredArgsConstructor;
//import multicampussa.laams.home.dashboard.dto.RealTimeDashboardResDto;
//import multicampussa.laams.manager.domain.center.CenterRepository;
//import multicampussa.laams.manager.domain.exam.Exam;
//import multicampussa.laams.manager.domain.exam.ExamRepository;
//import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class RealtimeDashboardService {
//
//    public final CenterRepository centerRepository;
//    public final ExamRepository examRepository;
//    public final ExamExamineeRepository examExamineeRepository;
//
//    public Map<String, Object> getRealTimeExamStatus(String authority) {
//        Map<String, Object> realtimeExamMap = new HashMap<String, Object>();
//        // 응시자는 대시보드 조회할 수 없음(감독관, 센터관리자, 운영자 다 봐도 될 듯)
//        if (authority.equals("ROLE_EXAMINEE")) {
//            throw new IllegalArgumentException("대시보드 조회 권한이 없습니다.");
//        }
//        // 현재 진행중인 시험 리스트 조회
//        List<Exam> exams = examRepository.
//
//        List<RealTimeDashboardResDto>
//
//    }
//
//}
