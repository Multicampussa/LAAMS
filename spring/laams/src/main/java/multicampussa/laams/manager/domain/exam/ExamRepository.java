package multicampussa.laams.manager.domain.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    // 센터 번호로 시험 리스트 만들기
    List<Exam> findByCenterNo(Long centerNo);

    // 특정 년도, 특정 월의 시험 조회
    @Query("SELECT e FROM Exam e WHERE YEAR(e.examDate) = :year AND MONTH(e.examDate) = :month")
    List<Exam> findExamsByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
