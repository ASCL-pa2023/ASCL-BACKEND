package esgi.ascl.tournament.domain.entities;

import esgi.ascl.game.domain.entities.Team;

public class TeamStats {
    private Team team;
    private Long tournamentId;
    private Integer wins;
    private Integer losses;
    private Integer setsWon;

    public Team getTeam() {
        return team;
    }

    public TeamStats setTeam(Team team) {
        this.team = team;
        return this;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public TeamStats setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
        return this;
    }

    public Integer getWins() {
        return wins;
    }

    public TeamStats setWins(Integer wins) {
        this.wins = wins;
        return this;
    }

    public Integer getLosses() {
        return losses;
    }

    public TeamStats setLosses(Integer losses) {
        this.losses = losses;
        return this;
    }

    public Integer getSetsWon() {
        return setsWon;
    }

    public TeamStats setSetsWon(Integer setsWon) {
        this.setsWon = setsWon;
        return this;
    }
}
