package esgi.ascl.game.infra.web.response;

public class ScoreResponse {
    public Long id;
    public int value;
    public Long setId;
    public Long teamId;

    public ScoreResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public ScoreResponse setValue(int value) {
        this.value = value;
        return this;
    }

    public ScoreResponse setSetId(Long setId) {
        this.setId = setId;
        return this;
    }

    public ScoreResponse setTeamId(Long teamId) {
        this.teamId = teamId;
        return this;
    }
}
