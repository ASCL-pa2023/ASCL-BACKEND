package esgi.ascl.tournament.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tournament_type")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TournamentTypeO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "name")
    private String name;
    ///enum des types de tournois ?


    public long getId() {
        return id;
    }

    public TournamentTypeO setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TournamentTypeO setName(String name) {
        this.name = name;
        return this;
    }
}
