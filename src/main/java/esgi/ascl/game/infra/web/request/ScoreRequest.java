package esgi.ascl.game.infra.web.request;

public class ScoreRequest {
    public Long setId;
    public Long teamId;
    public Integer score;

    public ScoreRequest setSetId(Long setId) {
        this.setId = setId;
        return this;
    }

    public ScoreRequest setTeamId(Long teamId) {
        this.teamId = teamId;
        return this;
    }

    public ScoreRequest setScore(Integer score) {
        this.score = score;
        return this;
    }

    public Long getSetId() {
        return setId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Integer getScore() {
        return score;
    }
}
