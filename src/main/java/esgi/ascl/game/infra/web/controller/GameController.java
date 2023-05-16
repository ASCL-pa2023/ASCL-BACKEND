package esgi.ascl.game.infra.web.controller;

import esgi.ascl.User.domain.exceptions.UserNotFoundExceptions;
import esgi.ascl.User.domain.mapper.UserMapper;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.exeptions.GameNotFoundException;
import esgi.ascl.game.domain.exeptions.RefereeIsPlayerException;
import esgi.ascl.game.domain.exeptions.TeamNotFoundException;
import esgi.ascl.game.domain.mapper.GameMapper;
import esgi.ascl.game.domain.service.GameService;
import esgi.ascl.game.infra.web.request.AssignRefereeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/game")
public class GameController {
    private final GameService gameService;
    private final UserService userService;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
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

    @PostMapping("assignReferee")
    public ResponseEntity<?> assignReferee(@RequestBody AssignRefereeRequest assignRefereeRequest) throws GameNotFoundException {
        try {
            userService.getById(assignRefereeRequest.getRefereeId());
        } catch (UserNotFoundExceptions e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        try {
            gameService.assignReferee(assignRefereeRequest.getGameId(), assignRefereeRequest.getRefereeId());
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("players/{gameId}")
    public ResponseEntity<?> getPlayers(@PathVariable Long gameId){
        try {
            gameService.getById(gameId);
        } catch (GameNotFoundException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var players = gameService.getPlayers(gameId)
                .stream()
                .map(UserMapper::entityToResponse)
                .toList();


        return new ResponseEntity<>(players, HttpStatus.OK);
    }
}
