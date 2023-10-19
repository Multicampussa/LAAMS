package multicampussa.laams.manager.exception;

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
}
