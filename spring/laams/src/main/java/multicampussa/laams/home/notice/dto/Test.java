package multicampussa.laams.home.notice.dto;

import lombok.ToString;

import java.time.LocalDateTime;

public interface Test {

    Long getNoticeNo();

    Long getManagerNo();

    String getTitle();

    String getContent();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
