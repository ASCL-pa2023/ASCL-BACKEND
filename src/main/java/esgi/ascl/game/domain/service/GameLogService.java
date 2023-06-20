package esgi.ascl.game.domain.service;

import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.entities.GameLog;
import esgi.ascl.game.domain.mapper.GameLogMapper;
import esgi.ascl.game.infra.repository.GameLogRepository;
import esgi.ascl.game.infra.web.request.GameLogRequest;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class GameLogService {
    private final GameLogRepository gameLogRepository;

    public GameLogService(GameLogRepository gameLogRepository) {
        this.gameLogRepository = gameLogRepository;
    }

    public GameLog create(GameLogRequest gameLogRequest, Game game) {
        return gameLogRepository.save(GameLogMapper.toEntity(gameLogRequest, game));
    }

    public List<GameLog> getAllByGameId(Long gameId) {
        return gameLogRepository.findAllByGameId(gameId);
    }
}
