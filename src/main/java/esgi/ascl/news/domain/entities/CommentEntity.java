package esgi.ascl.news.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import esgi.ascl.User.Entitie.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "comment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "news_id")
    private NewsEntity news;
    private String content;
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public CommentEntity setUser(User user) {
        this.user = user;
        return this;
    }

    public NewsEntity getNews() {
        return news;
    }

    public CommentEntity setNews(NewsEntity news) {
        this.news = news;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public CommentEntity setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
