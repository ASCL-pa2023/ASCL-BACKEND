package esgi.ascl.training.domain.mapper;


import esgi.ascl.User.domain.mapper.UserMapper;
import esgi.ascl.training.domain.entitie.TrainingRegistration;
import esgi.ascl.training.infastructure.web.response.TrainingRegistrationResponse;

public class TrainingRegistrationMapper {
    public static TrainingRegistrationResponse toResponse(TrainingRegistration trainingRegistration){
        return new TrainingRegistrationResponse()
                .setId(trainingRegistration.getId())
                .setTraining(TrainingMapper.entityToResponse(trainingRegistration.getTraining()))
                .setPlayer(UserMapper.entityToResponse(trainingRegistration.getPlayer()));
    }

}
