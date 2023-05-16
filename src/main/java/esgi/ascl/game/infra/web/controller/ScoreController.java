package esgi.ascl.game.infra.web.controller;

import esgi.ascl.game.domain.entities.Score;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.exeptions.ScoreNotFoundException;
import esgi.ascl.game.domain.exeptions.SetNotFoundException;
import esgi.ascl.game.domain.exeptions.TeamNotFoundException;
import esgi.ascl.game.domain.mapper.ScoreMapper;
import esgi.ascl.game.domain.service.ScoreService;
import esgi.ascl.game.domain.service.SetService;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.game.infra.web.response.ScoreUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/score")
public class ScoreController {
    private static final int MIN_SCORE = 0;
    private static final int MAX_SCORE = 21;

    private final ScoreService scoreService;
    private final TeamService teamService;
    private final SetService setService;

    public ScoreController(ScoreService scoreService, TeamService teamService, SetService setService) {
        this.scoreService = scoreService;
        this.teamService = teamService;
        this.setService = setService;
    }


    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody ScoreUpdateRequest scoreUpdateRequest){
        try {
            teamService.getById(scoreUpdateRequest.teamId);
        } catch (TeamNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        try {
            setService.getById(scoreUpdateRequest.setId);
        } catch (SetNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        if(scoreUpdateRequest.scoreValue < MIN_SCORE || scoreUpdateRequest.scoreValue > MAX_SCORE)
            return new ResponseEntity<>("Score value must be between 0 and 21", HttpStatus.BAD_REQUEST);


        Score score;
        try {
            score = scoreService.getBySetIdAndTeamId(scoreUpdateRequest.setId, scoreUpdateRequest.teamId);
        } catch (ScoreNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        scoreService.updateValue(score.getId(), scoreUpdateRequest.scoreValue);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{scoreId}")
    public ResponseEntity<?> getById(@PathVariable Long scoreId){
        Score score;
        try {
            score = scoreService.getById(scoreId);
        } catch (ScoreNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ScoreMapper.toResponse(score), HttpStatus.OK);
    }
}
