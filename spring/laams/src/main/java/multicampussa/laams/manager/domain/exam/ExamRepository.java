package multicampussa.laams.manager.domain.exam;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Override
    Optional<Exam> findById(Long no);
}
