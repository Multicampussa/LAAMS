package multicampussa.laams.director.dto.director;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
//@AllArgsConstructor
@NoArgsConstructor
public class ExamStatusDto {

    private int exmaineeCnt;
    private int attendanceCnt;
    private int documentCnt;
    private boolean directorAttendance;

    public ExamStatusDto(int examineeCnt, int attendanceCnt, int documentCnt, boolean directorAttendance){
        this.exmaineeCnt = examineeCnt;
        this.attendanceCnt = attendanceCnt;
        this.documentCnt = documentCnt;
        this.directorAttendance = directorAttendance;
    }
}
