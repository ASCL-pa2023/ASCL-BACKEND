package esgi.ascl.game.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import esgi.ascl.User.domain.entities.User;
import esgi.ascl.tournament.domain.entities.Tournament;
import jakarta.persistence.*;
import org.joda.time.DateTime;

@Entity
@Table(name = "game")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "referee_id")
    private User referee;

    private DateTime hourly;

    private Long winner_id;

    @Enumerated(EnumType.STRING)
    private GameType type;
    @Enumerated(EnumType.STRING)
    private GameStatus status;


    public Long getId() {
        return id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Game setTournament(Tournament tournament) {
        this.tournament = tournament;
        return this;
    }

    public User getReferee() {
        return referee;
    }

    public Game setReferee(User referee) {
        this.referee = referee;
        return this;
    }

    public DateTime getHourly() {
        return hourly;
    }

    public Game setHourly(DateTime hourly) {
        this.hourly = hourly;
        return this;
    }

    public Long getWinner_id() {
        return winner_id;
    }

    public Game setWinner_id(Long winner_id) {
        this.winner_id = winner_id;
        return this;
    }

    public GameType getType() {
        return type;
    }

    public Game setType(GameType type) {
        this.type = type;
        return this;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Game setStatus(GameStatus status) {
        this.status = status;
        return this;
    }
}
