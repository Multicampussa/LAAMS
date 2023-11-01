package multicampussa.laams.global;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<T> {

    private String status;
    private int code;
    private T data;

    public ApiResponse(String status, int code, T data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }
}
