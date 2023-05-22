package esgi.ascl.Tournament.infrastructure.repositories;

import esgi.ascl.Tournament.domain.Entitie.PartnerCandidacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerCandidateRepository extends JpaRepository<PartnerCandidacy, Long> {
    PartnerCandidacy findBySurveyIdAndUserId(Long surveyId, Long userId);

    List<PartnerCandidacy> findAllBySurveyId(Long surveyId);
    List<PartnerCandidacy> findAllByUserId(Long userId);
}
