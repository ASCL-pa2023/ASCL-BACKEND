package esgi.ascl.game.domain.service;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.exeptions.GameNotFoundException;
import esgi.ascl.game.domain.mapper.GameMapper;
import esgi.ascl.game.infra.repository.GameRepository;
import esgi.ascl.game.infra.web.request.GameRequest;
import esgi.ascl.tournament.domain.service.TournamentService;
import org.springframework.stereotype.Service;


@Service
public class GameService {
    private final UserService userService;
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public GameService(UserService userService, GameRepository gameRepository, GameMapper gameMapper) {
        this.userService = userService;
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    public Game create(GameRequest gameRequest){
        return gameRepository.save(gameMapper.requestToEntity(gameRequest));
    }

    public Game getById(Long id) throws GameNotFoundException {
        return gameRepository.
                findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found"));
    }


    public void assignReferee(Long gameId, Long refereeId) {
        User referee = userService.getById(refereeId);
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


}
