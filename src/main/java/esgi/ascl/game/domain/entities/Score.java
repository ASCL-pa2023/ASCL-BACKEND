package esgi.ascl.game.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "score")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int value;

    @ManyToOne
    @JoinColumn(name = "set_id")
    private Set set;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    public Long getId() {
        return id;
    }

    public Score setId(Long id) {
        this.id = id;
        return this;
    }

    public int getValue(){
        return value;
    }

    public Score setValue(int value){
        this.value = value;
        return this;
    }

    public Set getSet() {
        return set;
    }

    public Score setSet(Set set) {
        this.set = set;
        return this;
    }

    public Team getTeam() {
        return team;
    }

    public Score setTeam(Team team) {
        this.team = team;
        return this;
    }
}
