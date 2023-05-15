package esgi.ascl.game.infra.web.request;

public class SetRequest {

    public Long id;
    public Long gameId;

    public SetRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public SetRequest setGameId(Long gameId) {
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
