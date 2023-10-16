package multicampussa.laams.manager.domain.examinee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamineeRepository extends JpaRepository<Examinee, Long> {

    @Override
    Optional<Examinee> findById(Long no);
}
