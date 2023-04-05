package esgi.ascl.news.infrastructure.web.responses;

import esgi.ascl.User.domain.entities.User;

public class CommentResponse {
    public Long id;
    public String content;
    public User user;
    public Long newsId;

    public Long getId() {
        return id;
    }

    public CommentResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public User getUser() {
        return user;
    }

    public CommentResponse setUser(User user) {
        this.user = user;
        return this;
    }

    public Long getNewsId() {
        return newsId;
    }

    public CommentResponse setNewsId(Long newsId) {
        this.newsId = newsId;
        return this;
    }
}
