package esgi.ascl.tournament.domain.entitie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tournament_type")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TournamentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "name")
    private String name;
    ///enum des types de tournois ?

}
