package esgi.ascl.training.domain.service;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.training.domain.entitie.Training;
import esgi.ascl.training.domain.entitie.TrainingRegistration;
import esgi.ascl.training.domain.exception.TrainingRegistrationException;
import esgi.ascl.training.infastructure.repository.TrainingRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingRegistrationService {
    private final TrainingRegistrationRepository trainingRegistrationRepository;

    public TrainingRegistrationService(TrainingRegistrationRepository trainingRegistrationRepository) {
        this.trainingRegistrationRepository = trainingRegistrationRepository;
    }

    public TrainingRegistration registration(Training training, User user){
        return trainingRegistrationRepository.save(
                new TrainingRegistration()
                        .setTraining(training)
                        .setPlayer(user)
        );
    }

    public TrainingRegistration getById(Long id){
        return trainingRegistrationRepository
                .findById(id)
                .orElseThrow(() -> new TrainingRegistrationException("Training registration not found"));
    }

    public List<TrainingRegistration> getAll(){
        return trainingRegistrationRepository.findAll();
    }

    public List<TrainingRegistration> getAllByTrainingId(Long trainingId){
        return trainingRegistrationRepository.findAllByTrainingId(trainingId);
    }

    public List<TrainingRegistration> getAllByPlayerId(Long playerId){
        return trainingRegistrationRepository.findAllByPlayerId(playerId);
    }

    public void deleteById(TrainingRegistration trainingRegistration){
        trainingRegistration.setTraining(null);
        trainingRegistration.setPlayer(null);
        trainingRegistrationRepository.delete(trainingRegistration);
    }

}
