package esgi.ascl.training.infastructure.web.request;

import java.util.Date;

public class TrainingRequest {
    private long id;
    private Date date;
    private String timeSlot;
    private long trainingCategoryId;

    public long getId() {
        return id;
    }

    public TrainingRequest setId(long id) {
        this.id = id;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public TrainingRequest setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public TrainingRequest setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
        return this;
    }

    public long getTrainingCategoryId() {
        return trainingCategoryId;
    }

    public TrainingRequest setTrainingCategoryId(long trainingCategoryId) {
        this.trainingCategoryId = trainingCategoryId;
        return this;
    }
}
