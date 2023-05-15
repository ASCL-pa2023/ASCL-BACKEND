package esgi.ascl.game.infra.repository;

import esgi.ascl.game.domain.entities.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetRepository extends JpaRepository<Set, Long> {
}
