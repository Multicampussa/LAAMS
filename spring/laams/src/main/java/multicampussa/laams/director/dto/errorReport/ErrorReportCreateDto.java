package multicampussa.laams.director.dto.errorReport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorReportCreateDto {

    private String title;
    private String content;
    private String type;
    private LocalDateTime errorTime;
//    private Long directorNo;

}
