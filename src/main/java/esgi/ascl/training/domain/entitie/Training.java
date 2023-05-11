package esgi.ascl.training.domain.entitie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name="date")
    private Date date;

    @Column(name="time_slot")
    private String timeSlot;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "training_category_id")
    TrainingCategory trainingCategory;


    public Long getId() {
        return id;
    }

    public Training setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Training setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public Training setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
        return this;
    }

    public TrainingCategory getTrainingCategory() {
        return trainingCategory;
    }

    public Training setTrainingCategory(TrainingCategory trainingCategory) {
        this.trainingCategory = trainingCategory;
        return this;
    }
}
