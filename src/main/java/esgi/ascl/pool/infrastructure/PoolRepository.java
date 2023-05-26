package esgi.ascl.pool.infrastructure;

import esgi.ascl.pool.domain.entity.Pool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoolRepository extends JpaRepository<Pool, Long> {
}
