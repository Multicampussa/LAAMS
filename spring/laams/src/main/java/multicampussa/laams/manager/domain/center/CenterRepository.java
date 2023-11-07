package multicampussa.laams.manager.domain.center;

import multicampussa.laams.manager.domain.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center, Long> {

    Optional<Center> findByName(String name);

    Center findByNo(Long centerNo);


    @Query(value = "SELECT e FROM Exam e WHERE e.center.no " +
            "IN (SELECT c.no FROM Center c WHERE c.centerManager.id = :centerManagerId) " +
            "AND year(e.examDate) = :year AND month(e.examDate) = :month AND (day(e.examDate) = :day OR :day = 0) " +
            "ORDER BY e.examDate asc")
    List<Exam> findAllByCenterManagerIdContainingMonthAndDay(String centerManagerId, int year, int month, int day);

    @Query(value = "SELECT c FROM Center c JOIN c.director d WHERE d.id = :id")
    Center findByDirectorId(@Param("id") String id);

    List<Center> findByRegion(String region);
}
