package esgi.ascl.game.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import esgi.ascl.User.domain.entities.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_team")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;


    public Long getId() {
        return id;
    }

    public UserTeam setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserTeam setUser(User user) {
        this.user = user;
        return this;
    }

    public Team getTeam() {
        return team;
    }

    public UserTeam setTeam(Team team) {
        this.team = team;
        return this;
    }

}
