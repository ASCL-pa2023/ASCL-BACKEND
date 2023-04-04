package esgi.ascl.news.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tag")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "news_id")
    private NewsEntity news;


    public Long getId() {
        return id;
    }

    public TagEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TagEntity setName(String name) {
        this.name = name;
        return this;
    }

    public NewsEntity getNews() {
        return news;
    }

    public TagEntity setNews(NewsEntity news) {
        this.news = news;
        return this;
    }
}
