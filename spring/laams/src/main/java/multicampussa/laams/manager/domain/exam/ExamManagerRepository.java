package multicampussa.laams.manager.domain.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamManagerRepository extends JpaRepository<ExamManager, Long> {

    @Query("SELECT ee.exam.no, ee.exam.examDate, ee.exam.center.address FROM ExamExaminee ee " +
            "WHERE ee.examManager IN (SELECT em.no FROM ExamManager em " +
            "WHERE em.director = :directorNo)")
    List<Exam> findAllByDirectorNo(@Param("directorNo") Long directorNo);

}
