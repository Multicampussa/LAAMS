package multicampussa.laams.director.repository.errorReport;

import multicampussa.laams.director.domain.ErrorReport.ErrorReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorReportRepository extends JpaRepository<ErrorReport, Long> {

    // no로 조회
    ErrorReport findByNo(Long no);
}
