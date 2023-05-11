package esgi.ascl.training.infastructure.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingCategoryRequest {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
}
