package esgi.ascl.tournament.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import esgi.ascl.game.domain.entities.Team;
import jakarta.persistence.*;

@Entity
@Table(name = "tournament_inscription")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TournamentInscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Long getId() {
        return id;
    }

    public TournamentInscription setId(Long id) {
        this.id = id;
        return this;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public TournamentInscription setTournament(Tournament tournament) {
        this.tournament = tournament;
        return this;
    }

    public Team getTeam() {
        return team;
    }

    public TournamentInscription setTeam(Team team) {
        this.team = team;
        return this;
    }
}
