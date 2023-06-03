package esgi.ascl.game.domain.mapper;

import esgi.ascl.User.infrastructure.web.response.GameResponse;
import esgi.ascl.game.domain.entities.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public static GameResponse entityToResponse(Game game) {
        return new GameResponse()
                .setId(game.getId())
                .setTournamentId(game.getTournament().getId())
                .setRefereeId(game.getReferee().getId())
                .setHourly(game.getHourly());
    }

}
