package esgi.ascl.tournament.infrastructure.repositories;

import esgi.ascl.tournament.domain.entitie.TournamentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentTypeRepository extends JpaRepository<TournamentType, Long> {
    TournamentType getTournamentTypeById(Long id);

    TournamentType getTournamentTypeByName(String name);
}
