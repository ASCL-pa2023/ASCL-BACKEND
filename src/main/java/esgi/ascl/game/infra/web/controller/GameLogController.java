package esgi.ascl.game.infra.web.controller;

import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.exeptions.GameNotFoundException;
import esgi.ascl.game.domain.mapper.GameLogMapper;
import esgi.ascl.game.domain.service.GameLogService;
import esgi.ascl.game.domain.service.GameService;
import esgi.ascl.game.infra.web.request.GameLogRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/game-logs")
public class GameLogController {
    private final GameLogService gameLogService;
    private final GameService gameService;

    public GameLogController(GameLogService gameLogService, GameService gameService) {
        this.gameLogService = gameLogService;
        this.gameService = gameService;
    }


    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody GameLogRequest gameLogRequest) {
        Game game;
        try {
            game = gameService.getById(gameLogRequest.gameId);
        } catch (GameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var gameLog = gameLogService.create(gameLogRequest, game);
        return new ResponseEntity<>(GameLogMapper.toResponse(gameLog), HttpStatus.CREATED);
    }


    @GetMapping("{gameId}/logs")
    public ResponseEntity<?> getLogs(@PathVariable Long gameId) {
        try {gameService.getById(gameId);
        } catch (GameNotFoundException e) {return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);}

        var logs = gameLogService.getAllByGameId(gameId)
                .stream()
                .map(GameLogMapper::toResponse)
                .toList();

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
