package multicampussa.laams.home.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCreateDto {
    private String title;
    private String content;
//    private String memberId;
    private String imagePath;
}
