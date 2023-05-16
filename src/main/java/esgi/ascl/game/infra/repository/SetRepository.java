package esgi.ascl.game.infra.repository;

import esgi.ascl.game.domain.entities.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SetRepository extends JpaRepository<Set, Long> {
    Optional<List<Set>> findAllByGameId(Long gameId);
}
