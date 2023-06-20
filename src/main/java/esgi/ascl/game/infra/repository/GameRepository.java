package esgi.ascl.game.infra.repository;

import esgi.ascl.game.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllByTournamentId(Long tournamentId);
}
