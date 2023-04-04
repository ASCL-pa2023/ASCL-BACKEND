package esgi.ascl.news.infrastructure.web.requests;


import java.util.List;

public class NewsRequest {
    public Long userId;
    public String title;
    public String content;
    public List<String> tagNames;


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

    public List<String> getTagNames() {
        return tagNames;
    }

    public NewsRequest setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
        return this;
    }

}
