package esgi.ascl.tournament.domain.entities;

import esgi.ascl.game.infra.web.response.TeamResponse;

public class TeamRatio {
    private TeamResponse team;
    private Double ratio;

    public TeamResponse getTeam() {
        return team;
    }
    public TeamRatio setTeam(TeamResponse team) {
        this.team = team;
        return this;
    }

    public Double getRatio() {
        return ratio;
    }

    public TeamRatio setRatio(Double ratio) {
        this.ratio = ratio;
        return this;
    }
}
