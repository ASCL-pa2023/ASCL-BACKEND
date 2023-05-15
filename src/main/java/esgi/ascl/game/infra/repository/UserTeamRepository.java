package esgi.ascl.game.infra.repository;

import esgi.ascl.game.domain.entities.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    UserTeam findByUserIdAndAndTeamId(Long userId, Long teamId);
    void deleteUserTeamByUserIdAndAndTeamId(Long userId, Long teamId);
}
