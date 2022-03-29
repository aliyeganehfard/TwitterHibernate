package controller.exception;

public class OutOfBoundLength extends RuntimeException{
    public OutOfBoundLength() {
    }

    public OutOfBoundLength(String message) {
        super(message);
    }

    public OutOfBoundLength(String message, Throwable cause) {
        super(message, cause);
    }
}
