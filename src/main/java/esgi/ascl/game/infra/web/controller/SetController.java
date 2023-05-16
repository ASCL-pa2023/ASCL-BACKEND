package esgi.ascl.game.infra.web.controller;

import esgi.ascl.game.domain.entities.Set;
import esgi.ascl.game.domain.exeptions.GameNotFoundException;
import esgi.ascl.game.domain.exeptions.SetNotFoundException;
import esgi.ascl.game.domain.mapper.SetMapper;
import esgi.ascl.game.domain.service.GameService;
import esgi.ascl.game.domain.service.SetService;
import esgi.ascl.game.infra.web.request.SetRequest;
import esgi.ascl.news.domain.mapper.CommentMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/set")
public class SetController {
    private final SetService setService;
    private final GameService gameService;

    public SetController(SetService setService, GameService gameService) {
        this.setService = setService;
        this.gameService = gameService;
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody SetRequest setRequest){
        try {
            gameService.getById(setRequest.gameId);
        } catch (GameNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var set = setService.createSet(setRequest);
        return new ResponseEntity<>(SetMapper.toResponse(set), HttpStatus.OK);
    }

    @GetMapping("{setId}")
    public ResponseEntity<?> getById(@PathVariable Long setId){
        Set set;
        try {
            set = setService.getById(setId);
        } catch (SetNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(SetMapper.toResponse(set), HttpStatus.OK);
    }
}
