package esgi.ascl.game.domain.service;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.exeptions.TeamNotFoundException;
import esgi.ascl.game.infra.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserTeamService userTeamService;

    public TeamService(TeamRepository teamRepository, UserTeamService userTeamService) {
        this.teamRepository = teamRepository;
        this.userTeamService = userTeamService;
    }

    public Team createTeam(Game game) {
        var team = new Team()
                .setGame(game);
        return teamRepository.save(team);
    }

    public Team getById(Long id) {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new TeamNotFoundException("Team not found"));
    }

    public List<Team> getAllByGameId(Long gameId) {
        return teamRepository.findAllByGameId(gameId);
    }


    public void addUser(Long teamId, User user) {
        teamRepository
                .findById(teamId)
                .ifPresentOrElse(
                        t -> userTeamService.createTeam(t, user),
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
