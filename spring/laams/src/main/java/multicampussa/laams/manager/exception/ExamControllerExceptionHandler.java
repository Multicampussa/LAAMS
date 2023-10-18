package multicampussa.laams.manager.exception;

import multicampussa.laams.manager.exception.ErrorResponse;
import multicampussa.laams.manager.exception.ExamNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExamControllerExceptionHandler {

    @ExceptionHandler(ExamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleExamNotFoundException(ExamNotFoundException ex) {
        return new ErrorResponse("examNotFound", ex.getMessage());
    }
}
