package multicampussa.laams.manager.domain.exam;

import multicampussa.laams.director.domain.Director;
import multicampussa.laams.manager.dto.director.response.DirectorListResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamDirectorRepository extends JpaRepository<ExamDirector, Long> {
    List<Director> findByExamNo(Long examNo);
}
