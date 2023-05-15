package esgi.ascl.game.infra.web.response;

public class TeamResponse {
    public Long id;
    public Long gameId;

    public TeamResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public TeamResponse setGameId(Long gameId) {
        this.gameId = gameId;
        return this;
    }
}
