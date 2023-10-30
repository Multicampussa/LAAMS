package multicampussa.laams.manager.domain.centerManager;

import multicampussa.laams.manager.domain.center.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CenterManagerRepository extends JpaRepository<CenterManager, Long> {
    Optional<CenterManager> findById(String id);

    Boolean existsById(String id);
}
