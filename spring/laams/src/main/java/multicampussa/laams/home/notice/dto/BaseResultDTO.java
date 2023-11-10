package multicampussa.laams.home.notice.dto;

import lombok.Data;

@Data
public class BaseResultDTO {

    String message;

    int httpStatus;

    Object data;
}
