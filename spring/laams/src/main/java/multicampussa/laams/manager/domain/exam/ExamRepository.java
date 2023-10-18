package multicampussa.laams.manager.domain.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

//    @Query()
//    List<Exam> findAllByDirectorNo(@Param("directorNo") Long directorNo);
//
//    @Query("select b from back b join b.rental r join r.member m where m.isCha = :isCha")
//    Page<Back> findAllByIsCha(@Param("isCha") boolean isCha, Pageable pageable);
//
//
//    @Query("SELECT e.examDate, s.supervisorName FROM Exam e JOIN e.supervisors s WHERE e.id = :examId")
//    List<Object[]> findExamDateAndSupervisorById(@Param("examId") Long examId);
//    @Query("SELECT e FROM Exam e WHERE e.supervisors.managerId = :managerId")
//    List<Exam> findExamsByManagerId(@Param("managerId") Long managerId);

}
