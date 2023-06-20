package esgi.ascl.training.infastructure.web.request;

public class TrainingRegistrationRequest {
    private Long trainingId;
    private Boolean recurring;
    private Long userId;

    public Long getTrainingId() {
        return trainingId;
    }

    public TrainingRegistrationRequest setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
        return this;
    }

    public Boolean getRecurring() {
        return recurring;
    }

    public TrainingRegistrationRequest setRecurring(Boolean recurring) {
        this.recurring = recurring;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public TrainingRegistrationRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}
