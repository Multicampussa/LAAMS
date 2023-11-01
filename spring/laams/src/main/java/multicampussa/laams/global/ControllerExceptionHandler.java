package multicampussa.laams.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomExceptions.ExamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleExamNotFoundException(CustomExceptions.ExamNotFoundException ex) {
        return new ErrorResponse("examNotFound", ex.getMessage());
    }

    @ExceptionHandler(CustomExceptions.ManagerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleManagerNotFoundException(CustomExceptions.ManagerNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("managerNotFound", ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>("error", HttpStatus.NOT_FOUND.value(), errorResponse), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomExceptions.CenterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCenterNotFoundException(CustomExceptions.CenterNotFoundException ex) {
        return new ErrorResponse("centerNotFound", ex.getMessage());
    }

    @ExceptionHandler(CustomExceptions.ExamineeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleExamineeNotFoundException(CustomExceptions.ExamineeNotFoundException ex) {
        return new ErrorResponse("examineeNotFound", ex.getMessage());
    }

    @ExceptionHandler(CustomExceptions.DirectorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleDirectorNotFoundException(CustomExceptions.DirectorNotFoundException ex) {
        return new ErrorResponse("directorNotFound", ex.getMessage());
    }

    @ExceptionHandler(CustomExceptions.ErrorReportNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleErrorReportNotFoundException(CustomExceptions.ErrorReportNotFoundException ex) {
        return new ErrorResponse("errorReportNotFound", ex.getMessage());
    }

    @ExceptionHandler(CustomExceptions.ExamDirectorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleExamDirectorNotFoundException(CustomExceptions.ExamDirectorNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("examDirectorNotFound", ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>("error", HttpStatus.NOT_FOUND.value(), errorResponse), HttpStatus.NOT_FOUND);
    }

    // 권한 오류
    @ExceptionHandler(CustomExceptions.UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiResponse<ErrorResponse>> UnauthorizedException(CustomExceptions.UnauthorizedException ex) {
        ErrorResponse errorResponse = new ErrorResponse("UnauthorizedException", ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>("error", HttpStatus.UNAUTHORIZED.value(), errorResponse), HttpStatus.UNAUTHORIZED);
    }
}
