package esgi.ascl.training.infastructure.web.response;

import java.util.Date;

public class TrainingResponse {
    private long id;
    private Date date;
    private String timeSlot;
    private TrainingCategoryResponse trainingCategoryResponse;

    public long getId() {
        return id;
    }

    public TrainingResponse setId(long id) {
        this.id = id;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public TrainingResponse setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public TrainingResponse setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
        return this;
    }

    public TrainingCategoryResponse getTrainingCategoryResponse() {
        return trainingCategoryResponse;
    }

    public TrainingResponse setTrainingCategoryResponse(TrainingCategoryResponse trainingCategoryResponse) {
        this.trainingCategoryResponse = trainingCategoryResponse;
        return this;
    }
}
