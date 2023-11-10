package multicampussa.laams.manager.domain.exam;

import multicampussa.laams.home.dashboard.dto.DashboardExamDto;
import multicampussa.laams.home.dashboard.dto.DashboardExamineeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    // 시험 no로 조회
    Exam findByNo(Long examNo);

    // 시험 날짜로 조회
    @Query("SELECT e FROM Exam e WHERE DATE(e.examDate) = :targetDate")
    List<Exam> findExamByExamDate(java.sql.Date targetDate);

    // 센터 번호로 시험 리스트 만들기
    List<Exam> findByCenterNo(Long centerNo);

    // 특정 년도, 특정 월의 시험 조회
    @Query("SELECT COUNT(e) FROM Exam e WHERE YEAR(e.examDate) = :year AND MONTH(e.examDate) = :month")
    int countExamsByYearAndMonth(@Param("year") int year, @Param("month") int month);

    // 특정 년도, 특정 월, 특정 일 시험 조회
    @Query("SELECT e FROM Exam e WHERE YEAR(e.examDate) = :year AND MONTH(e.examDate) = :month")
    List<Exam> findExamsByYearAndMonth(
            @Param("year") Integer year,
            @Param("month") Integer month
    );

    // 특정 년도, 특정 월, 특정 일 시험 조회
    @Query("SELECT e FROM Exam e WHERE YEAR(e.examDate) = :year AND MONTH(e.examDate) = :month AND DAY(e.examDate) = :day")
    List<Exam> findExamsByYearMonthAndDay(
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("day") Integer day
    );

    // 대시보드용 센터별 시험 횟수(한달) 조회
//    @Query("select e.center.name, count(*) from Exam e where (year(e.examDate) = :year and month(e.examDate) = :month and e.center.name is not null) group by e.center")
//    List<Map<String, String>> getCenterExamMonthCount(@Param("year") int year, @Param("month") int month);

    @Query("select new multicampussa.laams.home.dashboard.dto.DashboardExamDto(e.center.name, count(*)) " +
            "from Exam e " +
            "where year(e.examDate) = :year " +
            "   and month(e.examDate) = :month " +
//            "   and e.center.name is not null " +
            "group by e.center")
    List<DashboardExamDto> getCenterExamMonthCount(@Param("year") int year, @Param("month") int month);

    // 대시보드용 센터별 월 응시자 수 조회
    @Query("select new multicampussa.laams.home.dashboard.dto.DashboardExamineeDto(e.center.name, count(*)) " +
            "from Exam e " +
            "join fetch ExamExaminee ee on e.no = ee.exam.no " +
            "where year(e.examDate) = :year " +
            "   and month(e.examDate) = :month " +
//            "   and e.center.name is not null " +
            "group by e.center")
    List<DashboardExamineeDto> getCenterExamineeMonthCount(@Param("year") int year, @Param("month") int month);

    @Query("select e from Exam e where e.center.no = :centerNo " +
            "and year(e.examDate) = :year and month(e.examDate) = :month and (day(e.examDate) = :day OR :day = 0) " +
            "order by e.examDate asc")
    List<Exam> findByCenterNoContainingDate(Long centerNo, int year, int month, int day);

    // 감독관 아이디로 현재 감독하는 시험들 찾기
    @Query("select ed.exam from ExamDirector ed where ed.director.id = :directorId and ed.confirm = '승인'")
    List<Exam> findByDirectorId(@Param("directorId") String directorId);

    // 감독관 아이디로 현재 감독하는 시험들 중 오늘 시험 찾기
    @Query("select e from Exam e where e.examDate >= :startOfToday AND e.examDate < :endOfToday and e.no in (select ed.exam.no from ExamDirector ed where ed.director.id = :directorId)")
    List<Exam> findByDirectorIdToday(String directorId, LocalDateTime startOfToday, LocalDateTime endOfToday);
}
