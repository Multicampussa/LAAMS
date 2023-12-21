package multicampussa.laams.home.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import multicampussa.laams.home.notice.domain.Notice;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeListResDto {
    private Long noticeNo;
    private Long managerNo;

    private String title;
//    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public void toEntity(Notice notice){
        this.noticeNo = notice.getNo();
        this.managerNo = notice.getMember().getNo();
        this.title = notice.getTitle();
//        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
        this.updatedAt = notice.getUpdatedAt();
    }
}
