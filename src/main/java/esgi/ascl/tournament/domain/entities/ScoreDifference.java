package esgi.ascl.tournament.domain.entities;

import esgi.ascl.game.domain.entities.Team;

public class ScoreDifference {
    private Team team;
    private Integer scoreDifference;

    public Team getTeam() {
        return team;
    }

    public ScoreDifference setTeam(Team team) {
        this.team = team;
        return this;
    }

    public Integer getScoreDifference() {
        return scoreDifference;
    }

    public ScoreDifference setScoreDifference(Integer scoreDifference) {
        this.scoreDifference = scoreDifference;
        return this;
    }

}
