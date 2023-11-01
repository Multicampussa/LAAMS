package multicampussa.laams.director.dto.director;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckAttendanceDto {

    private LocalDateTime attendanceTime;
    private Boolean attendance;
    private Boolean compensation;
    private String compensationType;

//    // @AllArgsConstructor 사용
//    public CheckAttendanceDto(Boolean attendance, Boolean compensation, String compensationType){
//        this.attendance = attendance;
//        this.compensation = compensation;
//        this.compensationType = compensationType;
//    }
}
