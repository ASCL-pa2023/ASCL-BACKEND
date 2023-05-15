package esgi.ascl.game.infra.repository;

import esgi.ascl.game.domain.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    Score findBySetIdAndTeamId(Long setId, Long teamId);
}
