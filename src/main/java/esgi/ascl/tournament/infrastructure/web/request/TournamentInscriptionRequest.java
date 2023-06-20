package esgi.ascl.tournament.infrastructure.web.request;

public class TournamentInscriptionRequest {
    public Long tournamentId;
    public Long teamId;

    public Long getTournamentId() {
        return tournamentId;
    }

    public TournamentInscriptionRequest setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
        return this;
    }

    public Long getTeamId() {
        return teamId;
    }

    public TournamentInscriptionRequest setTeamId(Long teamId) {
        this.teamId = teamId;
        return this;
    }
}
