package esgi.ascl.User.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "follower")
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private User follower;

    public Long getId() {
        return id;
    }
    public Follower setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Follower setUser(User user) {
        this.user = user;
        return this;
    }

    public User getFollower() {
        return follower;
    }

    public Follower setFollower(User followers) {
        this.follower = followers;
        return this;
    }
}
