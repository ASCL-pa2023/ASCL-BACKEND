package esgi.ascl.training.infastructure.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class TrainingRequest {
    private Long trainingCategoryId;
    private LocalDate date;
    private LocalTime timeSlot;
    private Boolean isRecurrent;
    @JsonProperty("trainingRecurrenceRequest")
    private TrainingRecurrenceRequest trainingRecurrenceRequest;
    private DayOfWeek dayOfRecurrence;
    private Integer nbPlayerMax;

    public TrainingRequest setTrainingCategoryId(Long trainingCategoryId) {
        this.trainingCategoryId = trainingCategoryId;
        return this;
    }

    public TrainingRequest setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public TrainingRequest setTimeSlot(LocalTime timeSlot) {
        this.timeSlot = timeSlot;
        return this;
    }

    public TrainingRequest setIsRecurrent(Boolean isRecurrent) {
        this.isRecurrent = isRecurrent;
        return this;
    }

    public TrainingRequest setTrainingRecurrenceRequest(TrainingRecurrenceRequest trainingRecurrenceRequest) {
        this.trainingRecurrenceRequest = trainingRecurrenceRequest;
        return this;
    }

    public TrainingRequest setDayOfRecurrence(DayOfWeek dayOfRecurrence) {
        this.dayOfRecurrence = dayOfRecurrence;
        return this;
    }

    public TrainingRequest setNbPlayerMax(Integer nbPlayerMax) {
        this.nbPlayerMax = nbPlayerMax;
        return this;
    }

    public Long getTrainingCategoryId() {
        return trainingCategoryId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTimeSlot() {
        return timeSlot;
    }

    public Boolean getIsRecurrent() {
        return isRecurrent;
    }

    public TrainingRecurrenceRequest getTrainingRecurrenceRequest() {
        return trainingRecurrenceRequest;
    }

    public DayOfWeek getDayOfRecurrence() {
        return dayOfRecurrence;
    }

    public Integer getNbPlayerMax() {
        return nbPlayerMax;
    }
}
