package esgi.ascl.conversation.infrastructure.web.requests;

public class ConversationRequest {
    public String title;
    public Long creatorId;

    public String getTitle() {
        return title;
    }

    public ConversationRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public ConversationRequest setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }
}
