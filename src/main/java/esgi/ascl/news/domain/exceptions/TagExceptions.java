package esgi.ascl.news.domain.exceptions;

public class TagExceptions extends Exception {
    public TagExceptions(String message) {
        super(message);
    }

    public TagExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
