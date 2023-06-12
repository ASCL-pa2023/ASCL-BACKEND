package esgi.ascl.game.infra.web.response;

import java.util.List;

public class SetResponse {
    public Long id;
    public Long gameId;
    public List<ScoreResponse> scores;

    public SetResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public SetResponse setGameId(Long gameId) {
        this.gameId = gameId;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Long getGameId() {
        return gameId;
    }

    public List<ScoreResponse> getScores() {
        return scores;
    }

    public SetResponse setScores(List<ScoreResponse> scores) {
        this.scores = scores;
        return this;
    }
}
