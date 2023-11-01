package multicampussa.laams.manager.domain.exam;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamDirectorRepository extends JpaRepository<ExamDirector, Long> {

    // 시험 정보로 ExamDirector 조회
    List<ExamDirector> findByExam(Exam exam);

    // 시험 no로 ExamDirector 조회
    List<ExamDirector> findByExamNo(Long examNo);

    // 시험 no과 감독 no로 조회
    ExamDirector findByExamNoAndDirectorNo(Long examNo, Long directorNo);

}
