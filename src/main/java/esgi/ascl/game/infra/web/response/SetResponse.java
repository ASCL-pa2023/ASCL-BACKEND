package esgi.ascl.game.infra.web.response;

public class SetResponse {
    public Long id;
    public Long gameId;

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
}
