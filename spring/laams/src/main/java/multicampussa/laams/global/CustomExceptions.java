package multicampussa.laams.global;

public class CustomExceptions {

    public static class ExamNotFoundException extends RuntimeException {
        public ExamNotFoundException(String message) {
            super(message);
        }
    }

    public static class ManagerNotFoundException extends RuntimeException {
        public ManagerNotFoundException(String message) {
            super(message);
        }
    }

    public static class CenterNotFoundException extends RuntimeException {
        public CenterNotFoundException(String message) {
            super(message);
        }
    }

    public static class ExamineeNotFoundException extends RuntimeException {
        public ExamineeNotFoundException(String message) {super(message);}
    }

    public static class DirectorNotFoundException extends RuntimeException {
        public DirectorNotFoundException(String message) {super(message);}
    }

    public static class ErrorReportNotFoundException extends RuntimeException {
        public ErrorReportNotFoundException(String message) {super(message);}
    }

    public static class ExamDirectorNotFoundException extends RuntimeException {
        public ExamDirectorNotFoundException(String message) {super(message);}
    }

    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {super(message);}
    }
}
