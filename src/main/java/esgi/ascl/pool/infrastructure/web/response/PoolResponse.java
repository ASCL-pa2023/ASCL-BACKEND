package esgi.ascl.pool.infrastructure.web.response;

import esgi.ascl.game.infra.web.response.TeamResponse;

import java.util.List;

public class PoolResponse {
    public Long id;
    public List<TeamResponse> teams;
    public Long tournamentId;

    public PoolResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public PoolResponse setTeams(List<TeamResponse> teams) {
        this.teams = teams;
        return this;
    }

    public PoolResponse setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
        return this;
    }

    public Long getId() {
        return id;
    }

    public List<TeamResponse> getTeams() {
        return teams;
    }

    public Long getTournamentId() {
        return tournamentId;
    }
}
