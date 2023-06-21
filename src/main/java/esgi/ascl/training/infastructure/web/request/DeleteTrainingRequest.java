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
}
