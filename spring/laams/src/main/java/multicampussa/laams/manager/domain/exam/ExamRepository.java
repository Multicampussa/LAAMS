package multicampussa.laams.manager.domain.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
<<<<<<< 7915e318c35b343fddfba5584d6b440332d3ce2a
import java.util.Map;
=======
>>>>>>> 0188f518726b9f16fa5cbc3bf3b81f8a9141a146
import java.util.Optional;


@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    // 시험 no로 조회
    Exam findByNo(Long examNo);

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
    @Query("select count(*) from Exam e where (e.center.no = :centerNo and year(e.examDate) = :year and month(e.examDate) = :month) group by e.center")
    String getCenterExamMonthCount(@Param("centerNo") Long centerNo, @Param("year") int year, @Param("month") int month);

    // 대시보드용 센터별 월 응시자 수 조회
    @Query("select count(e) from Exam e join fetch ExamExaminee ee on e.no = ee.exam.no where (e.center.no = :centerNo and year(e.examDate) = :year and month(e.examDate) = :month) group by e.center")
    String getCenterExamineeMonthCount(@Param("centerNo") Long centerNo, @Param("year") int year, @Param("month") int month);
}
