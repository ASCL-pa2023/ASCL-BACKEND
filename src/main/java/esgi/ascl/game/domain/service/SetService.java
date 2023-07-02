package esgi.ascl.game.domain.service;

import esgi.ascl.game.domain.entities.Set;
import esgi.ascl.game.domain.exeptions.GameNotFoundException;
import esgi.ascl.game.domain.exeptions.SetNotFoundException;
import esgi.ascl.game.infra.repository.GameRepository;
import esgi.ascl.game.infra.repository.SetRepository;
import esgi.ascl.game.infra.web.request.ScoreRequest;
import esgi.ascl.game.infra.web.request.SetRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetService {
    private final SetRepository setRepository;
    private final GameRepository gameRepository;
    private final ScoreService scoreService;
    private final PlayService playService;
    private final TeamService teamService;

    public SetService(SetRepository setRepository, GameRepository gameRepository, ScoreService scoreService, PlayService playService, TeamService teamService) {
        this.setRepository = setRepository;
        this.gameRepository = gameRepository;
        this.scoreService = scoreService;
        this.playService = playService;
        this.teamService = teamService;
    }


    public Set createSet(SetRequest setRequest) {
        var game = gameRepository.findById(setRequest.getGameId()).orElseThrow(()-> new GameNotFoundException("Game not found"));
        var set = new Set()
                .setGame(game)
                .setScores(new ArrayList<>());
        var setCreated = setRepository.save(set);


        playService.getPlaysByGameId(setRequest.getGameId())
                .forEach(play -> {
                    var team = teamService.getById(play.getTeam().getId());

                    var scoreRequest = new ScoreRequest()
                            .setSetId(setCreated.getId())
                            .setTeamId(team.getId())
                            .setScore(0);
                    scoreService.createScore(scoreRequest);
                });

        return setCreated;
    }

    public Set save(Set set) {
        return setRepository.save(set);
    }


    public Set getById(Long id) {
        return setRepository
                .findById(id)
                .orElseThrow(() -> new SetNotFoundException("Set not found"));
    }


    public List<Set> getAllSetByGameId(Long gameId) {
        return setRepository
                .findAllByGameId(gameId)
                .orElse(null);
    }
}
