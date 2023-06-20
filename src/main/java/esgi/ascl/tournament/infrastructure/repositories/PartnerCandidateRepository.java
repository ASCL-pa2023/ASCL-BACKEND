package esgi.ascl.tournament.infrastructure.repositories;

import esgi.ascl.tournament.domain.entities.PartnerCandidacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerCandidateRepository extends JpaRepository<PartnerCandidacy, Long> {
    PartnerCandidacy findBySurveyIdAndUserId(Long surveyId, Long userId);

    List<PartnerCandidacy> findAllBySurveyId(Long surveyId);
    List<PartnerCandidacy> findAllByUserId(Long userId);
}
