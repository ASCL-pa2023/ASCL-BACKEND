package esgi.ascl.training.infastructure.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class TrainingRecurrenceRequest {
    @JsonProperty("startDate")
    private LocalDate startDate;
     @JsonProperty("endDate")
    private LocalDate endDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
