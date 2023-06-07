package esgi.ascl.game.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import esgi.ascl.pool.domain.entity.Pool;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "team")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "teams")
    private List<Pool> pools;

    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;

    public Long getId() {
        return id;
    }

    public Team setId(Long id) {
        this.id = id;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public Team setGame(Game game) {
        this.game = game;
        return this;
    }
}
