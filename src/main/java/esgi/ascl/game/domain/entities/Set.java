package esgi.ascl.game.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

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

    @OneToMany(mappedBy = "set", cascade = CascadeType.ALL)
    private List<Score> scores;


    public void addScore(Score score) {
        this.scores.add(score);
    }

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

    public List<Score> getScores() {
        return scores;
    }

    public Set setScores(List<Score> scores) {
        this.scores = scores;
        return this;
    }

}
