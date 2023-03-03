package esgi.ascl.news.infrastructure.web.requests;

public class UserLikeRequest {

    public Long userId;
    public Long newsId;

    public Long getUserId() {
        return userId;
    }
    public UserLikeRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
    public Long getNewsId() {
        return newsId;
    }
    public UserLikeRequest setNewsId(Long newsId) {
        this.newsId = newsId;
        return this;
    }
}
