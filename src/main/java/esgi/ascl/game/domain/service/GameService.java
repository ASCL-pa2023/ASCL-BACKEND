package esgi.ascl.game.domain.service;

import esgi.ascl.User.domain.entities.Role;
import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.exeptions.GameNotFoundException;
import esgi.ascl.game.domain.exeptions.RefereeIsPlayerException;
import esgi.ascl.game.domain.mapper.GameMapper;
import esgi.ascl.game.infra.repository.GameRepository;
import esgi.ascl.game.infra.web.request.GameRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
public class GameService {
    private final UserService userService;
    private final PlayService playService;
    private final TeamService teamService;
    private final UserTeamService userTeamService;
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public GameService(UserService userService, PlayService playService, TeamService teamService, UserTeamService userTeamService, GameRepository gameRepository, GameMapper gameMapper) {
        this.userService = userService;
        this.playService = playService;
        this.teamService = teamService;
        this.userTeamService = userTeamService;
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    public Game create(GameRequest gameRequest){
        return gameRepository.save(gameMapper.requestToEntity(gameRequest));
    }

    public Game getById(Long id) {
        return gameRepository.
                findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found"));
    }


    public void assignReferee(Long gameId, Long refereeId) {
        User referee = userService.getById(refereeId);
        //TODO: check if referee has referee role

        if(userPlayingGame(refereeId, gameId)){
            throw new RefereeIsPlayerException();
        }
        gameRepository
                .findById(gameId)
                .ifPresentOrElse(
                    game -> {
                        game.setReferee(referee);
                        gameRepository.save(game);
                    },
                    () -> {
                        throw new GameNotFoundException("Game not found");
                    }
                );
    }


    public boolean userPlayingGame(Long userId, Long gameId) {
        AtomicBoolean isPlaying = new AtomicBoolean(false);
        var pl = playService.getPlaysByGameId(gameId);
        pl.forEach(play -> {
            var team = teamService.getById(play.getTeam().getId());
            if (userTeamService.getByUserAndTeam(userId, team.getId()) != null) {
                isPlaying.set(true);
            }
        });
        return isPlaying.get();
    }


}
