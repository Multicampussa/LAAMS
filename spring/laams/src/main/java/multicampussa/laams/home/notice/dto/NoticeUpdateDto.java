package multicampussa.laams.home.notice.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeUpdateDto {
//    private Long managerNo;
    private Long noticeNo;
    private String title;
    private String content;


}
