package esgi.ascl.pool.infrastructure;

import esgi.ascl.pool.domain.entity.Pool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoolRepository extends JpaRepository<Pool, Long> {
    List<Pool> findAllByTournamentId(Long tournamentId);
}
