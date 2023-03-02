package esgi.ascl.news.infrastructure.web.requests;


public class NewsRequest {
    public Long userId;
    public String title;
    public String content;


    public Long getUserId() {
        return userId;
    }

    public NewsRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
    public String getTitle() {
        return title;
    }

    public NewsRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NewsRequest setContent(String content) {
        this.content = content;
        return this;
    }

}
