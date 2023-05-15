package esgi.ascl.game.infra.web.controller;

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
        var team = teamService.getById(scoreUpdateRequest.teamId);
        if (team == null)
            return new ResponseEntity<>("Team not found", HttpStatus.NOT_FOUND);

        var set = setService.getById(scoreUpdateRequest.setId);
        if (set == null)
            return new ResponseEntity<>("Set not found", HttpStatus.NOT_FOUND);

        if(scoreUpdateRequest.scoreValue < MIN_SCORE || scoreUpdateRequest.scoreValue > MAX_SCORE)
            return new ResponseEntity<>("Score value must be between 0 and 21", HttpStatus.BAD_REQUEST);

        var score = scoreService.getBySetIdAndTeamId(scoreUpdateRequest.setId, scoreUpdateRequest.teamId);
        if (score == null)
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);

        scoreService.updateValue(score.getId(), scoreUpdateRequest.scoreValue);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{scoreId}")
    public ResponseEntity<?> getById(@PathVariable Long scoreId){
        var score = scoreService.getById(scoreId);
        if (score == null)
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ScoreMapper.toResponse(score), HttpStatus.OK);
    }
}
