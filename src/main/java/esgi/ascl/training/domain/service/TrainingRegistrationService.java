package esgi.ascl.training.domain.service;

import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.training.domain.entitie.Training;
import esgi.ascl.training.domain.entitie.TrainingRegistration;
import esgi.ascl.training.domain.exception.TrainingRegistrationException;
import esgi.ascl.training.infastructure.repository.TrainingRegistrationRepository;
import esgi.ascl.training.infastructure.web.request.TrainingRegistrationRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingRegistrationService {
    private final TrainingRegistrationRepository trainingRegistrationRepository;
    private final TrainingService trainingService;
    private final UserService userService;

    public TrainingRegistrationService(TrainingRegistrationRepository trainingRegistrationRepository, TrainingService trainingService, UserService userService) {
        this.trainingRegistrationRepository = trainingRegistrationRepository;
        this.trainingService = trainingService;
        this.userService = userService;
    }

    public List<TrainingRegistration> registration(TrainingRegistrationRequest trainingRegistrationRequest){
        var training = trainingService.getById(trainingRegistrationRequest.getTrainingId());
        var user = userService.getById(trainingRegistrationRequest.getUserId());
        var registrations = new ArrayList<TrainingRegistration>();


        registrations.add(new TrainingRegistration().setTraining(training).setPlayer(user));

        if(trainingRegistrationRequest.getRecurring()){
            var trainings = trainingService.getAllRecurrences(training)
                    .stream()
                    .filter(t -> t.getDate().isAfter(training.getDate()))
                    .toList();

            for(Training t : trainings){
                if(trainingRegistrationRepository.findAllByTrainingId(t.getId())
                        .stream().anyMatch(tr -> tr.getPlayer().getId().equals(user.getId()))){
                    throw new TrainingRegistrationException("Training registration already exist");
                }
            }

            trainings.forEach(t -> registrations.add(new TrainingRegistration().setTraining(t).setPlayer(user)));
        }

        return trainingRegistrationRepository.saveAll(registrations);
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
