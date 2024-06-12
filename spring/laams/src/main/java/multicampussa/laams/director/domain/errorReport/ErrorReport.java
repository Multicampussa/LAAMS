package multicampussa.laams.director.domain.errorReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.director.dto.errorReport.ErrorReportCreateDto;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.home.member.domain.Member;

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
    private Member member;

    private String title;

    @Column(length = 10000)
    private String content;

    private String type;
    private LocalDateTime errorTime;

    public void toEntity(ErrorReportCreateDto errorReportCreateDto, Member member) {
        this.title = errorReportCreateDto.getTitle();
        this.content = errorReportCreateDto.getContent();
        this.type = errorReportCreateDto.getType();
        this.errorTime = errorReportCreateDto.getErrorTime();
        this.member = member;
    }
}
