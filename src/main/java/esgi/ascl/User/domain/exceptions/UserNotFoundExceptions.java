package esgi.ascl.User.domain.exceptions;

public class UserNotFoundExceptions extends RuntimeException{
    public UserNotFoundExceptions(String message) {
        super(message);
    }
}
