package esgi.ascl.tournament.infrastructure.repositories;

import esgi.ascl.tournament.domain.entities.TournamentTypeO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentTypeRepository extends JpaRepository<TournamentTypeO, Long> {
    TournamentTypeO getTournamentTypeById(Long id);

    TournamentTypeO getTournamentTypeByName(String name);
}
