package esgi.ascl.tournament.infrastructure.repositories;

import esgi.ascl.tournament.domain.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findAllByUserId(Long id);

    List<Survey> findAllByTournamentId(Long id);
}
