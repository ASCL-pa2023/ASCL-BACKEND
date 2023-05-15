package esgi.ascl.game.domain.service;

import esgi.ascl.game.domain.entities.Score;
import esgi.ascl.game.domain.exeptions.ScoreNotFoundException;
import esgi.ascl.game.infra.repository.ScoreRepository;
import esgi.ascl.game.infra.repository.SetRepository;
import esgi.ascl.game.infra.web.request.ScoreRequest;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final SetRepository setRepository;
    private final TeamService teamService;
    public ScoreService(ScoreRepository scoreRepository, SetRepository setRepository, TeamService teamService) {
        this.scoreRepository = scoreRepository;
        this.setRepository = setRepository;
        this.teamService = teamService;
    }


    public Score createScore(ScoreRequest scoreRequest) {
        var score = new Score()
                .setSet(setRepository.findById(scoreRequest.getSetId()).get())
                .setTeam(teamService.getById(scoreRequest.getTeamId()));
        return scoreRepository.save(score);
    }

    public Score getById(Long id) {
        return scoreRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Score not found"));
    }

    public Score getBySetIdAndTeamId(Long setId, Long teamId) {
        return scoreRepository.findBySetIdAndTeamId(setId, teamId);
    }

    public void updateValue(Long scoreId, int newValue){
        scoreRepository
                .findById(scoreId)
                .ifPresentOrElse(
                        score -> {
                            score.setValue(newValue);
                            scoreRepository.save(score);
                        },
                        () -> {
                            throw new ScoreNotFoundException("Team not found");
                        }
                );
    }

}
