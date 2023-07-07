package esgi.ascl.training.infastructure.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingCategoryRequest {
    private String name;
    private Integer ageMin;
    private Integer ageMax;
}
