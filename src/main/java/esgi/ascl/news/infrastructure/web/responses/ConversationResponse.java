package esgi.ascl.news.infrastructure.web.responses;

public class ConversationResponse {
    public Long id;
    public String title;

    public Long getId() {
        return id;
    }

    public ConversationResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ConversationResponse setTitle(String title) {
        this.title = title;
        return this;
    }
}
