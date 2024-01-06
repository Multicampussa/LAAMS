package multicampussa.laams.home.member.repository;

import multicampussa.laams.home.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<multicampussa.laams.home.member.domain.Member, Long> {
    boolean existsByEmail(String email);
    boolean existsById(String id);
    Optional<Member> findById(String id);
    Optional<Member> findByEmail(String email);

    List<Member> findByCenterNo(Long centerNo);
}
