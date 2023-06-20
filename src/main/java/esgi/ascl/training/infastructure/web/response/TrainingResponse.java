package esgi.ascl.training.infastructure.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingResponse {
    @JsonProperty("id")
    private long id;
    @JsonProperty("trainingCategory")
    private TrainingCategoryResponse trainingCategoryResponse;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("timeSlot")
    private LocalTime timeSlot;
    private Boolean isRecurrent;
    private DayOfWeek dayOfRecurrence;
    private Integer nbPlayerMax;
}
