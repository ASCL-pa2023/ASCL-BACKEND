package esgi.ascl.game.domain.mapper;

import esgi.ascl.User.infrastructure.web.response.UserResponse;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.infra.web.response.TeamResponse;

import java.util.List;

public class TeamMapper {
    public static TeamResponse toResponse(Team team, List<UserResponse> users) {
        return new TeamResponse()
                .setId(team.getId())
                .setUsers(users);
    }
}
