package esgi.ascl.game.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "play")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Play {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;


    public Long getId() {
        return id;
    }

    public Play setId(Long id) {
        this.id = id;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public Play setGame(Game game) {
        this.game = game;
        return this;
    }

    public Team getTeam() {
        return team;
    }

    public Play setTeam(Team team) {
        this.team = team;
        return this;
    }

}
