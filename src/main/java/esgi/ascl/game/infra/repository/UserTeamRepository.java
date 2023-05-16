package esgi.ascl.game.infra.repository;

import esgi.ascl.game.domain.entities.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    UserTeam findByUserIdAndAndTeamId(Long userId, Long teamId);
    Optional<UserTeam> findByUserId(Long userId);
    Optional<UserTeam> findAllByTeamId(Long teamId);
    void deleteUserTeamByUserIdAndAndTeamId(Long userId, Long teamId);
}
