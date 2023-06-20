package esgi.ascl.game.domain.service;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.domain.entities.UserTeam;
import esgi.ascl.game.domain.exeptions.UserTeamNotFound;
import esgi.ascl.game.infra.repository.UserTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTeamService {
    private final UserTeamRepository userTeamRepository;

    public UserTeamService(UserTeamRepository userTeamRepository) {
        this.userTeamRepository = userTeamRepository;
    }

    public UserTeam createTeam(Team team, User user) {
        var userTeam = new UserTeam()
                .setUser(user)
                .setTeam(team);
        return userTeamRepository.save(userTeam);
    }

    public void deleteUserFromTeam(Long userId, Long teamId) {
        var userTeam = userTeamRepository.findByUserIdAndAndTeamId(userId, teamId)
                .setTeam(null)
                .setUser(null);

        userTeamRepository.delete(userTeam);
    }

    public UserTeam getByUserAndTeam(Long userId, Long teamId) {
        return userTeamRepository.findByUserIdAndAndTeamId(userId, teamId);
    }

    public UserTeam getByUser(Long userId) {
        return userTeamRepository
                .findByUserId(userId)
                .orElseThrow(() -> new UserTeamNotFound("UserTeam not found"));
    }

    public List<UserTeam> getAllByTeam(Long teamId) {
        return userTeamRepository
                .findAllByTeamId(teamId)
                .orElseThrow(() -> new UserTeamNotFound("UserTeam not found"));
    }
}