package esgi.ascl.training.infastructure.web.response;

import esgi.ascl.User.infrastructure.web.response.UserResponse;

public class TrainingRegistrationResponse {
    private Long id;
    private TrainingResponse training;
    private UserResponse player;

    public Long getId() {
        return id;
    }

    public TrainingRegistrationResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public TrainingResponse getTraining() {
        return training;
    }

    public TrainingRegistrationResponse setTraining(TrainingResponse training) {
        this.training = training;
        return this;
    }

    public UserResponse getPlayer() {
        return player;
    }

    public TrainingRegistrationResponse setPlayer(UserResponse player) {
        this.player = player;
        return this;
    }
}
