package multicampussa.laams.manager.domain.examinee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamExamineeRepository extends JpaRepository<ExamExaminee, Long> {

    // 시험_응시자 엔티티에서 시험 no에 해당하는 응시자 전체 조회
    List<ExamExaminee> findByExamNo(Long examNo);

    // 출석이 true인 응시자 전체 조회
    List<ExamExaminee> findByAttendance(Boolean attendance);

    // 보상대상 여부가 true인 응시자 전체 조회
    List<ExamExaminee> findByCompensation(Boolean compensation);
}

