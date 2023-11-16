package multicampussa.laams.director.domain.errorReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.director.domain.director.Director;
import multicampussa.laams.director.dto.errorReport.ErrorReportCreateDto;
import multicampussa.laams.global.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name = "director_no")
    private Director director;

    private String title;

    @Column(length = 10000)
    private String content;

    private String type;
    private LocalDateTime errorTime;

    public void toEntity(ErrorReportCreateDto errorReportCreateDto, Director director) {
        this.title = errorReportCreateDto.getTitle();
        this.content = errorReportCreateDto.getContent();
        this.type = errorReportCreateDto.getType();
        this.errorTime = errorReportCreateDto.getErrorTime();
        this.director = director;
    }
}
