package esgi.ascl.tournament.infrastructure.repositories;

import esgi.ascl.tournament.domain.entitie.Tournament;
import esgi.ascl.tournament.domain.entitie.TournamentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Tournament getTournamentById(Long id);

    List<Tournament> getTournamentsByTournamentType(TournamentType tournamentType);

    List<Tournament> getTournamentsByLocation(String location);

    List<Tournament> getTournamentsByDate(Date date);
}
