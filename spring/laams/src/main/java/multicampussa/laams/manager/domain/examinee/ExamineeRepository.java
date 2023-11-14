package multicampussa.laams.manager.domain.examinee;

import multicampussa.laams.manager.domain.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamineeRepository extends JpaRepository<Examinee, Long> {

    // 응시자 no로 조회
    Examinee findByNo(Long no);
}
