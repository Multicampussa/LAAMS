package multicampussa.laams.director.dto.Director;

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
//    private int documentCnt;

    public ExamStatusDto(int examineeCnt, int attendanceCnt){
        this.exmaineeCnt = examineeCnt;
        this.attendanceCnt = attendanceCnt;
//        this.documentCnt = documentCnt;
    }
}
