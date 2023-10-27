package multicampussa.laams.manager.exception;

import multicampussa.laams.manager.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestController
public class ExamControllerExceptionHandler {

    @ExceptionHandler(CustomExceptions.ExamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleExamNotFoundException(CustomExceptions.ExamNotFoundException ex) {
        return new ErrorResponse("examNotFound", ex.getMessage());
    }

    @ExceptionHandler(CustomExceptions.ManagerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleManagerNotFoundException(CustomExceptions.ManagerNotFoundException ex) {
        return new ErrorResponse("managerNotFound", ex.getMessage());
    }

    @ExceptionHandler(CustomExceptions.CenterNotFundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCenterNotFoundException(CustomExceptions.CenterNotFundException ex) {
        return new ErrorResponse("centerNotFound", ex.getMessage());
    }
}
