package esgi.ascl.news.infrastructure.web.responses;

import esgi.ascl.User.domain.entities.User;

import java.util.Date;
import java.util.List;

public class NewsResponse {

    public Long id;
    public User user;
    public String title;
    public String content;
    public List<TagResponse> tags;
    public Date creationDate;

    public Long getId() {
        return id;
    }

    public NewsResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public NewsResponse setUser(User user) {
        this.user = user;
        return this;
    }
    
    public String getTitle() {
        return title;
    }

    public NewsResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NewsResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public List<TagResponse> getTags() {
        return tags;
    }

    public NewsResponse setTags(List<TagResponse> tags) {
        this.tags = tags;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public NewsResponse setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
