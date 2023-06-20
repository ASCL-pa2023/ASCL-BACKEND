package esgi.ascl.game.domain.exeptions;

public class RefereeIsPlayerException extends RuntimeException {
    public RefereeIsPlayerException() {
        super("Referee can be a player in the game");
    }
}
