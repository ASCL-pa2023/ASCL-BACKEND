package esgi.ascl.training.infastructure.web.response;

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
    private long id;
    private Date date;
    private String timeSlot;
    private TrainingCategoryResponse trainingCategoryResponse;
}
