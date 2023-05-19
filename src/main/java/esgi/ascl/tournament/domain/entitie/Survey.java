package esgi.ascl.Tournament.domain.Entitie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import esgi.ascl.User.domain.entities.User;
import esgi.ascl.tournament.domain.entitie.Tournament;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "survey")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String content;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    private Date creationDate;


    public Long getId() {
        return id;
    }

    public Survey setId(Long id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Survey setContent(String content) {
        this.content = content;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Survey setUser(User user) {
        this.user = user;
        return this;
    }

    public esgi.ascl.tournament.domain.entitie.Tournament getTournament() {
        return tournament;
    }

    public Survey setTournament(Tournament tournament) {
        this.tournament = tournament;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Survey setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

}
