package esgi.ascl.game.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "game_log")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GameLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private Game game;

    private String content;

    @Column(name = "created_at")
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public GameLog setId(Long id) {
        this.id = id;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public GameLog setGame(Game game) {
        this.game = game;
        return this;
    }

    public String getContent() {
        return content;
    }

    public GameLog setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public GameLog setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
