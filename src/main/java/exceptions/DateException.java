package exceptions;

public class DateException extends RuntimeException {
    public DateException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
