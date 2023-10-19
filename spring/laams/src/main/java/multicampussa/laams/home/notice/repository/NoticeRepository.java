package multicampussa.laams.home.notice.repository;

import multicampussa.laams.home.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
