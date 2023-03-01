package esgi.ascl.news.domain.entities;

import esgi.ascl.User.Entitie.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_like")
public class UserLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private NewsEntity news;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    public Long getId() {
        return id;
    }

    public UserLikeEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public NewsEntity getNews() {
        return news;
    }

    public UserLikeEntity setNews(NewsEntity news) {
        this.news = news;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserLikeEntity setUser(User user) {
        this.user = user;
        return this;
    }
}
