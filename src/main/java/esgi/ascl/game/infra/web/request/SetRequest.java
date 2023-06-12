package esgi.ascl.game.infra.web.request;

public class SetRequest {

    public Long gameId;

    public SetRequest setGameId(Long gameId) {
        this.gameId = gameId;
        return this;
    }

    public Long getGameId() {
        return gameId;
    }
}
