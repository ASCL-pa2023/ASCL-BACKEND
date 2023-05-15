package esgi.ascl.game.infra.repository;

import esgi.ascl.game.domain.entities.Play;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayRepository extends JpaRepository<Play, Long> {
    List<Play> findAllByGameId(Long gameId);
}
