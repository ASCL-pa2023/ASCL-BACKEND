package esgi.ascl.tournament.infrastructure.repositories;

import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.entities.TournamentType;
import esgi.ascl.tournament.domain.entities.TournamentTypeO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Tournament getTournamentById(Long id);

    List<Tournament> getTournamentsByType(TournamentType type);

    List<Tournament> getTournamentsByLocation(String location);

    //List<Tournament> getTournamentsByDate(Date date);
}
