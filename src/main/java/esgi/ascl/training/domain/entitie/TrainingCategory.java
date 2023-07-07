package esgi.ascl.training.domain.entitie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="training_category")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TrainingCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer ageMin;
    private Integer ageMax;

    public TrainingCategory setId(Long id) {
        this.id = id;
        return this;
    }

    public TrainingCategory setName(String name) {
        this.name = name;
        return this;
    }

    public TrainingCategory setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
        return this;
    }

    public TrainingCategory setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
        return this;
    }
}
