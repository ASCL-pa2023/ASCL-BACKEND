package esgi.ascl.news.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "news_image")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class NewsImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private NewsEntity news;
    private String filename;
    private String url;
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public NewsEntity getNews() {
        return news;
    }

    public NewsImageEntity setNews(NewsEntity news) {
        this.news = news;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public NewsImageEntity setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public NewsImageEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public NewsImageEntity setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
