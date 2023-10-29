package multicampussa.laams.director.repository;

import multicampussa.laams.director.domain.Director;
import multicampussa.laams.manager.domain.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    @Query(value = "SELECT e, c FROM Exam e INNER JOIN Center c ON e.center.no = c.no " +
            "WHERE e.no IN (SELECT ed.no FROM ExamDirector ed WHERE ed.director.no = :directorNo) " +
            "AND year(e.examDate) = :year AND month(e.examDate) = :month AND (day(e.examDate) = :day OR :day = 0) " +
            "ORDER BY e.examDate asc")
    List<Exam> findAllByDirectorNoContainingMonthAndDay(@Param("directorNo") Long directorNo, @Param("year") Integer year, @Param("month") Integer month, @Param("day") Integer day);

    Director findById(String directorId);
}
