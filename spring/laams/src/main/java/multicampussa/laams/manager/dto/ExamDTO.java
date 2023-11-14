package multicampussa.laams.manager.dto;

import java.time.LocalDateTime;

public interface ExamDTO {
    //    @Query(value="SELECT c.name as center, c.region, e.exam_date, e.running_time, e.no as examNo \n" +

    String getCenter();

    String getRegion();

    LocalDateTime getDate();

    int getRunningTime();

    long getExamNo();
}
