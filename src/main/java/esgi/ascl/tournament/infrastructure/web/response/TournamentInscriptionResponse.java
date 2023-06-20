package esgi.ascl.tournament.infrastructure.web.response;

public class TournamentInscriptionResponse {
    public Long id;
    public Long tournamentId;
    public Long teamId;


    public Long getId() {
        return id;
    }

    public TournamentInscriptionResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getTeamId() {
        return teamId;
    }

    public TournamentInscriptionResponse setTeamId(Long teamId) {
        this.teamId = teamId;
        return this;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public TournamentInscriptionResponse setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
        return this;
    }
}
