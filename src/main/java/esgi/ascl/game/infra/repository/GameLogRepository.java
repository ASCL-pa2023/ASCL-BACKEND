package esgi.ascl.game.infra.repository;

import esgi.ascl.game.domain.entities.GameLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameLogRepository extends JpaRepository<GameLog, Long> {
    List<GameLog> findAllByGameId(Long gameId);
}
