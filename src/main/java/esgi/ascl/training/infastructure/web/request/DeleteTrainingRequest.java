package esgi.ascl.training.infastructure.web.request;

public class DeleteTrainingRequest {
    private Long trainingId;
    private Boolean withRecurrence;

    public Long getTrainingId() {
        return trainingId;
    }

    public Boolean getWithRecurrence() {
        return withRecurrence;
    }
}
