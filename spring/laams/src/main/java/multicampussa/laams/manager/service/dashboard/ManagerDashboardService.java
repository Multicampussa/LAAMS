package multicampussa.laams.manager.service.dashboard;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.repository.errorReport.ErrorReportRepository;
import multicampussa.laams.manager.domain.exam.ExamDirectorRepository;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import multicampussa.laams.manager.dto.dashboard.response.DashboardResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerDashboardService {

    private ErrorReportRepository errorReportRepository;
    private ExamExamineeRepository examExamineeRepository;
    private ExamDirectorRepository examDirectorRepository;

    public List<DashboardResponse> getDashboard(int month) {

    }
}
