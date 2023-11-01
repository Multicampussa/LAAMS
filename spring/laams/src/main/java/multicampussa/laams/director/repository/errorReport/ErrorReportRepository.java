package multicampussa.laams.director.repository.errorReport;

import multicampussa.laams.director.domain.errorReport.ErrorReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ErrorReportRepository extends JpaRepository<ErrorReport, Long> {

    // no로 조회
    ErrorReport findByNo(Long no);

    // 달별 조회
    List<ErrorReport> findByCreatedAtMonth(int month);

    // 일별 조회
    List<ErrorReport> findByCreatedAtDate(int day);
}
