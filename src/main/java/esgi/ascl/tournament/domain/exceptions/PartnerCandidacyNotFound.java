package esgi.ascl.tournament.domain.exceptions;

public class PartnerCandidacyNotFound extends RuntimeException {
    public PartnerCandidacyNotFound(Long id) {
        super("PartnerCandidacy with id " + id + " not found");
    }
}
