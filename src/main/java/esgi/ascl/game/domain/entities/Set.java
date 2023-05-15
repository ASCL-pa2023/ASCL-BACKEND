package esgi.ascl.game.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "set")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private Game game;

    public Long getId() {
        return id;
    }

    public Set setId(Long id) {
        this.id = id;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public Set setGame(Game game) {
        this.game = game;
        return this;
    }

}
