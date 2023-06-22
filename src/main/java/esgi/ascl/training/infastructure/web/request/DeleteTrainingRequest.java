package esgi.ascl.training.infastructure.web.request;

public class DeleteTrainingRequest {
    private Long trainingId;
    private Boolean withRecurrences;

    public Long getTrainingId() {
        return trainingId;
    }

    public Boolean getWithRecurrences() {
        return withRecurrences;
    }

    public DeleteTrainingRequest setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
        return this;
    }

    public DeleteTrainingRequest setWithRecurrences(Boolean withRecurrences) {
        this.withRecurrences = withRecurrences;
        return this;
    }

}
