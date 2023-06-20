package esgi.ascl.game.domain.exeptions;

public class UserTeamNotFound extends RuntimeException {
    public UserTeamNotFound(String message) {
        super(message);
    }
}
