package esgi.ascl.Tournament.Entitie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tournament")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;


}
