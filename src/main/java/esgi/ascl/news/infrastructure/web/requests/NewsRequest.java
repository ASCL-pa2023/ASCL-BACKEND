package esgi.ascl.news.infrastructure.web.requests;


public class NewsRequest {
    public String title;
    public String content;


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
