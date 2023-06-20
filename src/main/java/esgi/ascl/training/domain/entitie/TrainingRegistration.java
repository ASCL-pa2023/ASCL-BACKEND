package esgi.ascl.training.domain.entitie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import esgi.ascl.User.domain.entities.User;
import jakarta.persistence.*;

@Entity
@Table(name = "training_registration")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TrainingRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Training training;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User player;

    public Long getId() {
        return id;
    }

    public TrainingRegistration setId(Long id) {
        this.id = id;
        return this;
    }

    public Training getTraining() {
        return training;
    }

    public TrainingRegistration setTraining(Training training) {
        this.training = training;
        return this;
    }

    public User getPlayer() {
        return player;
    }

    public TrainingRegistration setPlayer(User player) {
        this.player = player;
        return this;
    }
}
