package esgi.ascl.game.domain.mapper;

import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.entities.GameLog;
import esgi.ascl.game.infra.web.request.GameLogRequest;
import esgi.ascl.game.infra.web.response.GameLogResponse;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GameLogMapper {
    public static GameLogResponse toResponse(GameLog gameLog) {
        return new GameLogResponse()
                .setId(gameLog.getId())
                .setGameId(gameLog.getGame().getId())
                .setContent(gameLog.getContent())
                .setCreatedAt(gameLog.getCreatedAt());
    }

    public static GameLog toEntity(GameLogRequest gameLogRequest, Game game) {
        return new GameLog()
                .setGame(game)
                .setContent(gameLogRequest.content)
                .setCreatedAt(new Date());
    }
}
