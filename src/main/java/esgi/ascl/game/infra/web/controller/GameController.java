package esgi.ascl.game.infra.web.controller;

import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.exeptions.GameNotFoundException;
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
        Game game;
        try {
            game = gameService.getById(gameId);
        } catch (GameNotFoundException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(GameMapper.entityToResponse(game), HttpStatus.OK);
    }

}
