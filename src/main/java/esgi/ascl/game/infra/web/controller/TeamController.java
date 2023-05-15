package esgi.ascl.game.infra.web.controller;

import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.game.domain.mapper.TeamMapper;
import esgi.ascl.game.domain.service.GameService;
import esgi.ascl.game.domain.service.PlayService;
import esgi.ascl.game.domain.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/team")
public class TeamController {
    private final TeamService teamService;
    private final GameService gameService;
    private final UserService userService;
    private final PlayService playService;

    public TeamController(TeamService teamService, GameService gameService, UserService userService, PlayService playService) {
        this.teamService = teamService;
        this.gameService = gameService;
        this.userService = userService;
        this.playService = playService;
    }

    @PostMapping("create/{gameId}")
    public ResponseEntity<?> create(@PathVariable Long gameId){
        var game = gameService.getById(gameId);
        if (game == null)
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);

        var team = teamService.createTeam(game);
        return new ResponseEntity<>(TeamMapper.toResponse(team), HttpStatus.OK);
    }


    @GetMapping("{teamId}")
    public ResponseEntity<?> getById(@PathVariable Long teamId){
        var team = teamService.getById(teamId);
        if (team == null)
            return new ResponseEntity<>("Team not found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(TeamMapper.toResponse(team), HttpStatus.OK);
    }

    @PostMapping("addPlayer/{teamId}/{playerId}")
    public ResponseEntity<?> addPlayer(@PathVariable Long teamId, @PathVariable Long playerId){
        var team = teamService.getById(teamId);
        if (team == null)
            return new ResponseEntity<>("Team not found", HttpStatus.NOT_FOUND);

        var player = userService.getById(playerId);
        if (player == null)
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);


        try {
            teamService.addUser(team.getId(), player);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User " + playerId + " add to team " + teamId, HttpStatus.OK);
    }

    @PostMapping("removePlayer/{teamId}/{playerId}")
    public ResponseEntity<?> removePlayer(@PathVariable Long teamId, @PathVariable Long playerId){
        var team = teamService.getById(teamId);
        if (team == null)
            return new ResponseEntity<>("Team not found", HttpStatus.NOT_FOUND);

        var player = userService.getById(playerId);
        if (player == null)
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);

        try {
            teamService.deleteUser(team.getId(), player.getId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User " + playerId + " remove from team " + teamId, HttpStatus.OK);
    }
}
