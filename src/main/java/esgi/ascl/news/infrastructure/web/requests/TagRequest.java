package esgi.ascl.news.infrastructure.web.requests;

public class TagRequest {
    public String name;
    public Long newsId;

    public String getName() {
        return name;
    }

    public TagRequest setName(String name) {
        this.name = name;
        return this;
    }

    public Long getNewsId() {
        return newsId;
    }

    public TagRequest setNewsId(Long newsId) {
        this.newsId = newsId;
        return this;
    }
}
