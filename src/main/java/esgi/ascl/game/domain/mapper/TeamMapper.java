package esgi.ascl.game.domain.mapper;

import esgi.ascl.User.domain.mapper.UserMapper;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.User.infrastructure.web.response.UserResponse;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.game.infra.web.response.TeamResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamMapper {

    private final UserService userService;
    private final TeamService teamService;

    public TeamMapper(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }

    public TeamResponse toResponse(Team team) {
        var users = teamService
                .getAllUserByTeam(team.getId())
                .stream()
                .map(UserMapper::entityToResponse)
                .toList();

        return new TeamResponse()
                .setId(team.getId())
                .setUsers(users);
    }
}
