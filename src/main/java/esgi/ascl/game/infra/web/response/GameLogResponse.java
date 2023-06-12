package esgi.ascl.game.infra.web.response;


import java.util.Date;

public class GameLogResponse {
    public Long id;
    public Long gameId;
    public String content;
    public Date createdAt;

    public GameLogResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public GameLogResponse setGameId(Long gameId) {
        this.gameId = gameId;
        return this;
    }

    public GameLogResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public GameLogResponse setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
