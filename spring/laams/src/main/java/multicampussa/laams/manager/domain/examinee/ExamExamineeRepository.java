package multicampussa.laams.manager.domain.examinee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamExamineeRepository extends JpaRepository<ExamExaminee, Long> {

    List<ExamExaminee> findByExamExamNo(Long exaNo);
    @Query("SELECT COUNT(e) FROM ExamExaminee e WHERE e.exam.examNo = :examNo AND e.attendance = true")
    int countByExamNoAndAttendanceTrue(@Param("examNo") Long examNo);

    @Query("SELECT COUNT(DISTINCT e.examinee.examineeNo) FROM ExamExaminee e WHERE e.exam.examNo = :examNo AND e.attendance = true")
    int countDistinctExamineeByExamNoAndAttendanceTrue(@Param("examNo") Long examNo);

    @Query("SELECT COUNT(e) FROM ExamExaminee e WHERE e.exam.examNo = :examNo AND e.compensation = true")
    int countByExamNoAndCompensationTrue(@Param("examNo") Long examNo);
}
