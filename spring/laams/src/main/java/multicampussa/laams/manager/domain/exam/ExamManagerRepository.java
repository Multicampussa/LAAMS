package multicampussa.laams.manager.domain.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamManagerRepository extends JpaRepository<ExamManager, Long> {
    @Query("select em.no from ExamManager em where em.director = :directorNo")
    List<ExamManager> findAllByDirectorNo(@Param("directorNo") Long directorNo);
}
