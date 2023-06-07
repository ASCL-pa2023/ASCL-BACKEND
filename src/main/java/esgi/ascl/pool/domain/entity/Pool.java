package esgi.ascl.pool.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.tournament.domain.entities.Tournament;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pool")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "pool_team",
            joinColumns = @JoinColumn(name = "pool_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Game> games;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Tournament tournament;


    public Long getId() {
        return id;
    }

    public Pool setId(Long id) {
        this.id = id;
        return this;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Pool setTeams(List<Team> teams) {
        this.teams = teams;
        return this;
    }

    public List<Game> getGames() {
        return games;
    }

    public Pool setGames(List<Game> games) {
        this.games = games;
        return this;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Pool setTournament(Tournament tournament) {
        this.tournament = tournament;
        return this;
    }
}
