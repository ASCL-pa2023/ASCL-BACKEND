package esgi.ascl.tournament.domain.exceptions;

public class TournamentInscriptionNotFound extends RuntimeException {
    public TournamentInscriptionNotFound(Long id) {
        super("Tournament inscription with id " + id + " not found");
    }
}
