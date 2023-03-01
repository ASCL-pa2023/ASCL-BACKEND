package esgi.ascl.news.infrastructure.web.requests;

public class CommentRequest {
    public Long userId;
    public Long newsId;
    public String content;

    public Long getUserId() {
        return userId;
    }

    public CommentRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getNewsId() {
        return newsId;
    }

    public CommentRequest setNewsId(Long newsId) {
        this.newsId = newsId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentRequest setContent(String content) {
        this.content = content;
        return this;
    }
}
