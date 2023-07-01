package esgi.ascl.game.infra.web.controller;

import esgi.ascl.game.domain.entities.Score;
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
            scoreService.getById(scoreUpdateRequest.scoreId);
        } catch (ScoreNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        if(scoreUpdateRequest.value < MIN_SCORE || scoreUpdateRequest.value > MAX_SCORE)
            return new ResponseEntity<>("Score value must be between 0 and 21", HttpStatus.BAD_REQUEST);


        scoreService.updateValue(scoreUpdateRequest.scoreId, scoreUpdateRequest.value);
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

    @GetMapping("set/{setId}/team/{teamId}")
    public ResponseEntity<?> getBySetIdAndTeamId(@PathVariable Long setId, @PathVariable Long teamId){
        try {setService.getById(setId);
        } catch (SetNotFoundException e){return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);}

        try {teamService.getById(teamId);
        } catch (TeamNotFoundException e){return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);}


        Score score;
        try {
            score = scoreService.getBySetIdAndTeamId(setId, teamId);
        } catch (ScoreNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ScoreMapper.toResponse(score), HttpStatus.OK);
    }

    @GetMapping("set/{setId}")
    public ResponseEntity<?> getAllBySetId(@PathVariable Long setId){
        try {setService.getById(setId);
        } catch (SetNotFoundException e){return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);}

        var scores = scoreService.getAllBySetId(setId)
                .stream()
                .map(ScoreMapper::toResponse)
                .toList();
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }
}
