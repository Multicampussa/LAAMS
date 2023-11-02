package multicampussa.laams.director.repository.errorReport;

import multicampussa.laams.director.domain.errorReport.ErrorReport;
import multicampussa.laams.manager.dto.dashboard.response.DashboardErrorReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ErrorReportRepository extends JpaRepository<ErrorReport, Long> {

    // no로 조회
    ErrorReport findByNo(Long no);

    // 일별 조회
    @Query("SELECT er FROM ErrorReport  er WHERE DATE(er.createdAt) = :targetDate")
    List<ErrorReport> findErrorReportByDate(@Param("targetDate") java.sql.Date targetDate);

}
