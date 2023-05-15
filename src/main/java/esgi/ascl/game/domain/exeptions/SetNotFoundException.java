package esgi.ascl.game.domain.exeptions;

public class SetNotFoundException extends RuntimeException {
    public SetNotFoundException(String setNotFound) {
        super(setNotFound);
    }
}
