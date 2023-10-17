package multicampussa.laams.home.member.repository;

import multicampussa.laams.director.domain.Director;
import multicampussa.laams.manager.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    boolean existsById(String id);
    Optional<Manager> findById(String id);
}
