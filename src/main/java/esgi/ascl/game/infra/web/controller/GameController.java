package esgi.ascl.game.infra.web.controller;

import esgi.ascl.game.domain.mapper.GameMapper;
import esgi.ascl.game.domain.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/game")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @GetMapping("{gameId}")
    public ResponseEntity<?> getById(@PathVariable Long gameId){
        var game = gameService.getById(gameId);
        if (game == null)
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(GameMapper.entityToResponse(game), HttpStatus.OK);
    }

}
