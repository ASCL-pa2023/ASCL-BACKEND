package esgi.ascl.game.domain.service;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.exeptions.TeamNotFoundException;
import esgi.ascl.game.infra.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserTeamService userTeamService;
    private final UserService userService;

    public TeamService(TeamRepository teamRepository, UserTeamService userTeamService, UserService userService) {
        this.teamRepository = teamRepository;
        this.userTeamService = userTeamService;
        this.userService = userService;
    }

    public Team createTeam() {
        return teamRepository.save(new Team());
    }

    public Team assignGame(Long teamId, Game game) {
        return teamRepository
                .findById(teamId)
                .map(team -> team.setGame(game))
                .map(teamRepository::save)
                .orElseThrow(() -> new TeamNotFoundException("Team not found"));
    }

    public Team getById(Long id) {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new TeamNotFoundException("Team not found"));
    }

    public List<Team> getAllByGameId(Long gameId) {
        return teamRepository.findAllByGameId(gameId);
    }


    public void addUsers(Long teamId, Set<Long> usersId) {
        var users = usersId
                .stream()
                .map(userService::getById);


        teamRepository
                .findById(teamId)
                .ifPresentOrElse(
                        t -> {
                            users.forEach(user -> userTeamService.createTeam(t, user));
                        },
                        () -> {
                            throw new TeamNotFoundException("Team not found");
                        }
                );
    }

    public void deleteUser(Long teamId, Long userId) {
        teamRepository
                .findById(teamId)
                .ifPresentOrElse(
                        t -> userTeamService.deleteUserFromTeam(userId, t.getId()),
                        () -> {
                            throw new TeamNotFoundException("Team not found");
                        }
                );
    }

    public List<User> getAllUserByTeam(Long teamId) {
        var users = new ArrayList<User>();
        teamRepository
                .findById(teamId)
                .ifPresentOrElse(
                        t -> userTeamService.getAllByTeam(t.getId())
                                .forEach(userTeam -> users.add(userTeam.getUser())),
                        () -> {
                            throw new TeamNotFoundException("Team not found");
                        }
                );
        return users;
    }


}
