package multicampussa.laams.home.dashboard.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.dashboard.dto.DashboardExamDto;
import multicampussa.laams.home.dashboard.dto.DashboardExamineeDto;
import multicampussa.laams.home.member.repository.MemberManagerRepository;
import multicampussa.laams.home.notice.domain.Notice;
import multicampussa.laams.home.notice.dto.NoticeCreateDto;
import multicampussa.laams.home.notice.dto.NoticeListResDto;
import multicampussa.laams.home.notice.dto.NoticeUpdateDto;
import multicampussa.laams.home.notice.repository.NoticeRepository;
import multicampussa.laams.manager.domain.center.CenterRepository;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import multicampussa.laams.manager.domain.manager.Manager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class DashboardService {

    public final CenterRepository centerRepository;
    public final ExamRepository examRepository;
    public final ExamExamineeRepository examExamineeRepository;
    public final NoticeRepository noticeRepository;
    public final MemberManagerRepository managerRepository;


//    센터별 월별 시험 횟수 조회
    public Map<String, Object> getCenterExamMonthCount(String authority, int year, int month) {
        Map<String, Object> centerExamMap = new HashMap<String, Object>();

        if (authority.equals("ROLE_DIRECTOR")) {
            throw new IllegalArgumentException("대시보드 조회 권한이 없습니다.");
        }
        List<DashboardExamDto> theNumberOfExam = examRepository.getCenterExamMonthCount(year, month);

//        if (theNumberOfExam == null) {
//            theNumberOfExam = "0";
//        }
        centerExamMap.put("examCount", theNumberOfExam);


        // 반환
        return centerExamMap;
    }

//    센터별 월별 응시자 수 조회
    public Map<String, Object> getCenterExamineeMonthCount(String authority, int year, int month) {
        Map<String, Object> centerExamineeMap = new HashMap<String, Object>();

        if (authority.equals("ROLE_DIRECTOR")) {
            throw new IllegalArgumentException("대시보드 조회 권한이 없습니다.");
        }
        List<DashboardExamineeDto> theNumberOfExaminee = examRepository.getCenterExamineeMonthCount(year, month);

        centerExamineeMap.put("examineeCount", theNumberOfExaminee);


        // 반환
        return centerExamineeMap;
    }



}
