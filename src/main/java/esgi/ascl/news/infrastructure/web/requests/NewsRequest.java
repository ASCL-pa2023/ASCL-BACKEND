package esgi.ascl.news.infrastructure.web.requests;

import java.util.Date;

public class NewsRequest {
    public String title;
    public String content;
    public Date creationDate;


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

    public Date getCreationDate() {
        return creationDate;
    }

    public NewsRequest setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
