package multicampussa.laams.director.dto.Director;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckAttendanceDto {

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
