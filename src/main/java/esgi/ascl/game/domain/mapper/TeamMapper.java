package esgi.ascl.game.domain.mapper;

import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.infra.web.response.TeamResponse;

public class TeamMapper {
    public static TeamResponse toResponse(Team team) {
        return new TeamResponse()
                .setId(team.getId())
                .setGameId(team.getId());
    }
}
