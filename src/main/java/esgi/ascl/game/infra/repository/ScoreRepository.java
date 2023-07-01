package esgi.ascl.game.infra.repository;

import esgi.ascl.game.domain.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findBySetIdAndTeamId(Long setId, Long teamId);

    List<Score> findAllBySetId(Long setId);
}
