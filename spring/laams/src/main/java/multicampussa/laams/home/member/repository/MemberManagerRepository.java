package multicampussa.laams.home.member.repository;

import multicampussa.laams.manager.domain.manager.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberManagerRepository extends JpaRepository<Manager, Long> {
    boolean existsById(String id);
    boolean existsByEmail(String email);
    Optional<Manager> findById(String id);
    Optional<Manager> findByEmail(String email);
}
