package esgi.ascl.game.domain.mapper;

import esgi.ascl.User.infrastructure.web.response.GameResponse;
import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.service.GameService;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    private static GameService gameService;
    private static TeamMapper teamMapper;


    public GameMapper(GameService gameService, TeamMapper teamMapper) {
        GameMapper.gameService = gameService;
        this.teamMapper = teamMapper;
    }

    public static GameResponse entityToResponse(Game game) {
        return new GameResponse()
                .setId(game.getId())
                .setTournamentId(game.getTournament().getId())
                .setRefereeId(game.getReferee() == null ? null : game.getReferee().getId())
                .setHourly(game.getHourly())
                .setWinner_id(game.getWinner_id())
                .setType(game.getType())
                .setTeams(
                        gameService.getTeams(game.getId())
                                .stream()
                                .map(team -> teamMapper.toResponse(team))
                                .toList()
                );
    }

}
