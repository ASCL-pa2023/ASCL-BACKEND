package esgi.ascl.news.infrastructure.web.responses;

public class CommentResponse {
    public Long id;
    public String content;
    public Long userId;
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

    public Long getUserId() {
        return userId;
    }

    public CommentResponse setUserId(Long userId) {
        this.userId = userId;
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
