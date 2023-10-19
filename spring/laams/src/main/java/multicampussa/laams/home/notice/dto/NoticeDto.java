package multicampussa.laams.home.notice.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
    private String title;
    private String content;


}
