package esgi.ascl.tournament.infrastructure.repositories;

import esgi.ascl.tournament.domain.entities.TournamentInscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentInscriptionRepository extends JpaRepository<TournamentInscription, Long> {
    List<TournamentInscription> findAllByTournamentId(Long tournamentId);

    List<TournamentInscription> findAllByTeamId(Long teamId);

    TournamentInscription findByTournamentIdAndTeamId(Long tournamentId, Long teamId);
}
