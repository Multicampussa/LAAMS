package multicampussa.laams.manager.domain.exam;

import multicampussa.laams.director.dto.ExamListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamManagerRepository extends JpaRepository<ExamManager, Long> {

    @Query(value = "SELECT e, c FROM Exam e " +
            "INNER JOIN Center c ON e.center.no = c.no WHERE e.no " +
            "IN (SELECT ee.exam.no FROM ExamExaminee ee WHERE ee.examManager.no " +
            "IN (SELECT em.no FROM ExamManager em WHERE em.director.no = :directorNo)) ORDER BY e.examDate asc")
    List<Exam> findAllByDirectorNo(@Param("directorNo") Long directorNo);
}
