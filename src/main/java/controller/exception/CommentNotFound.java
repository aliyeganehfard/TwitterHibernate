package controller.exception;

public class CommentNotFound extends RuntimeException{
    public CommentNotFound() {
    }

    public CommentNotFound(String message) {
        super(message);
    }

    public CommentNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
