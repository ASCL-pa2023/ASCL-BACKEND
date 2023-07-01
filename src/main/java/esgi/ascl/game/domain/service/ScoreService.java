package esgi.ascl.game.domain.service;

import esgi.ascl.game.domain.entities.Score;
import esgi.ascl.game.domain.exeptions.ScoreNotFoundException;
import esgi.ascl.game.infra.repository.ScoreRepository;
import esgi.ascl.game.infra.repository.SetRepository;
import esgi.ascl.game.infra.web.request.ScoreRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    private static final int MAX_SCORE = 21;
    private final ScoreRepository scoreRepository;
    private final SetRepository setRepository;
    private final TeamService teamService;
    public ScoreService(ScoreRepository scoreRepository, SetRepository setRepository, TeamService teamService) {
        this.scoreRepository = scoreRepository;
        this.setRepository = setRepository;
        this.teamService = teamService;
    }


    public Score createScore(ScoreRequest scoreRequest) {
        var set = setRepository
                .findById(scoreRequest.getSetId())
                .orElseThrow(() -> new ScoreNotFoundException("Set not found"));

        var score = new Score()
                .setValue(scoreRequest.score)
                .setTeam(teamService.getById(scoreRequest.getTeamId()))
                .setSet(set);

        var scoreSaved = scoreRepository.save(score);
        set.addScore(scoreSaved);

        return scoreSaved;
    }

    public Score getById(Long id) {
        return scoreRepository
                .findById(id)
                .orElseThrow(() -> new ScoreNotFoundException("Score not found"));
    }

    public Score getBySetIdAndTeamId(Long setId, Long teamId) {
        return scoreRepository
                .findBySetIdAndTeamId(setId, teamId)
                .orElseThrow(() -> new ScoreNotFoundException("Score not found"));
    }

    public List<Score> getAllBySetId(Long setId) {
        return scoreRepository
                .findAllBySetId(setId);
    }

    public void updateValue(Long scoreId, int newValue){
        scoreRepository
                .findById(scoreId)
                .ifPresentOrElse(
                        score -> {
                            score.setValue(newValue);

                            if(newValue == MAX_SCORE)
                                score.getSet().setWinnerId(score.getTeam().getId());

                            scoreRepository.save(score);
                        },
                        () -> {
                            throw new ScoreNotFoundException("Team not found");
                        }
                );
    }

}
