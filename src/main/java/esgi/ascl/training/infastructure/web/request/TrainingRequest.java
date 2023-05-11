package esgi.ascl.training.infastructure.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequest {
    private long id;
    private Date date;
    private String timeSlot;
    private long trainingCategoryId;
}
