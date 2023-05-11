package esgi.ascl.training.infastructure.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingResponse {
    @JsonProperty("id")
    private long id;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("timeSlot")
    private String timeSlot;
    @JsonProperty("trainingCategory")
    private TrainingCategoryResponse trainingCategoryResponse;
}
