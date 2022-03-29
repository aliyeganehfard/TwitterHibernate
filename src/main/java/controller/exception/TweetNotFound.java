package controller.exception;

public class TweetNotFound extends RuntimeException{
    public TweetNotFound() {
    }

    public TweetNotFound(String message) {
        super(message);
    }

    public TweetNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
