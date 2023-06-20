package esgi.ascl.training.domain.entitie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "training")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "training_category_id")
    private TrainingCategory trainingCategory;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    private LocalTime timeSlot;

    private Boolean isRecurrent;

    private DayOfWeek dayOfRecurrence;

    private Integer nbPlayerMax;

    @ManyToOne
    @JoinColumn(name = "recurrence_of_id")
    private Training recurrenceTraining;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date timestamp;



    public Training setId(Long id) {
        this.id = id;
        return this;
    }

    public Training setTrainingCategory(TrainingCategory trainingCategory) {
        this.trainingCategory = trainingCategory;
        return this;
    }

    public Training setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Training setTimeSlot(LocalTime timeSlot) {
        this.timeSlot = timeSlot;
        return this;
    }

    public Training setIsRecurrent(Boolean isRecurrent) {
        this.isRecurrent = isRecurrent;
        return this;
    }

    public Training setDayOfRecurrence(DayOfWeek dayOfRecurrence) {
        this.dayOfRecurrence = dayOfRecurrence;
        return this;
    }

    public Training setNbPlayerMax(Integer nbPlayerMax) {
        this.nbPlayerMax = nbPlayerMax;
        return this;
    }

    public Training setRecurrenceTraining(Training recurrenceTraining) {
        this.recurrenceTraining = recurrenceTraining;
        return this;
    }

    public TrainingCategory getTrainingCategory() {
        return trainingCategory;
    }

}
