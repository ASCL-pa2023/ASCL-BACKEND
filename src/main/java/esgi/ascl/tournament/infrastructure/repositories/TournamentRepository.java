package esgi.ascl.tournament.infrastructure.repositories;

import esgi.ascl.tournament.domain.entitie.Tournament;
import esgi.ascl.tournament.domain.entitie.TournamentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Tournament getTournamentById(Long id);

    List<Tournament> getTournamentsByTournamentType(TournamentType tournamentType);

    List<Tournament> getTournamentsByLocation(String location);

    //List<Tournament> getTournamentsByDate(Date date);
}
