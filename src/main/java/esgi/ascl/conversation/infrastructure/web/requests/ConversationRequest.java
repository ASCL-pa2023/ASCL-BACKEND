package esgi.ascl.conversation.infrastructure.web.requests;

public class ConversationRequest {
    public String title;

    public String getTitle() {
        return title;
    }

    public ConversationRequest setTitle(String title) {
        this.title = title;
        return this;
    }
}
