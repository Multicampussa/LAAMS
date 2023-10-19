package multicampussa.laams.manager.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String error;
    private String message;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }
}
