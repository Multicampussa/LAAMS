package multicampussa.laams.manager.domain.exam.center;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center, Long> {

    @Override
    Optional<Center> findById(Long no);
}
