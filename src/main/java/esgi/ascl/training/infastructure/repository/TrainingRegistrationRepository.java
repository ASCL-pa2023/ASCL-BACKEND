package esgi.ascl.training.infastructure.repository;

import esgi.ascl.training.domain.entitie.Training;
import esgi.ascl.training.domain.entitie.TrainingRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRegistrationRepository extends JpaRepository<TrainingRegistration, Long> {

    List<TrainingRegistration> findAllByTrainingId(Long trainingId);
    List<TrainingRegistration> findAllByPlayerId(Long playerId);
}
