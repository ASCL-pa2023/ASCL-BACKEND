package esgi.ascl.game.domain.mapper;

import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.User.infrastructure.web.response.GameResponse;
import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.infra.web.request.GameRequest;
import esgi.ascl.tournament.domain.service.TournamentService;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    private final TournamentService tournamentService;
    private final UserService userService;

    public GameMapper(TournamentService tournamentService, UserService userService) {
        this.tournamentService = tournamentService;
        this.userService = userService;
    }

    public Game requestToEntity(GameRequest gameRequest) {
        return new Game()
                .setTournament(
                        tournamentService.getById(gameRequest.getTournamentId())
                )
                .setReferee(
                        userService.getById(gameRequest.getRefereeId())
                )
                .setHourly(gameRequest.getHourly());
    }

    public static GameResponse entityToResponse(Game game) {
        return new GameResponse()
                .setId(game.getId())
                .setTournamentId(game.getTournament().getId())
                .setRefereeId(game.getReferee().getId())
                .setHourly(game.getHourly());
    }

}
