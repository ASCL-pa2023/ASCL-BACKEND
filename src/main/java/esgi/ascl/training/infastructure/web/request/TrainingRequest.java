package esgi.ascl.training.infastructure.web.request;

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
public class TrainingRequest {
    private Long trainingCategoryId;
    private Date date;
    private LocalTime timeSlot;
    private Boolean isRecurrent;
    private DayOfWeek dayOfRecurrence;
    private Integer nbPlayerMax;
}
