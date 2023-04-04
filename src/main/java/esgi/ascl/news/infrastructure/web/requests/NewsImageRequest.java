package esgi.ascl.news.infrastructure.web.requests;

public class NewsImageRequest {
    public Long newsId;
    public String filename;
    public String url;

    public Long getNewsId() {
        return newsId;
    }

    public NewsImageRequest setNewsId(Long newsId) {
        this.newsId = newsId;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public NewsImageRequest setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public NewsImageRequest setUrl(String url) {
        this.url = url;
        return this;
    }
}
