package multicampussa.laams.manager.domain.center;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center, Long> {

    Optional<Center> findByName(String name);
    Center findByNo(Long centerNo);
}
