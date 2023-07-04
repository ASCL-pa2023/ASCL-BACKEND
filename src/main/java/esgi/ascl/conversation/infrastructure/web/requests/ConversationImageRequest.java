package esgi.ascl.conversation.infrastructure.web.requests;

public class ConversationImageRequest {
    public Long conversationId;
    public String filename;
    public String url;

    public Long getConversationId() {
        return conversationId;
    }

    public ConversationImageRequest setConversationId(Long conversationId) {
        this.conversationId = conversationId;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public ConversationImageRequest setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ConversationImageRequest setUrl(String url) {
        this.url = url;
        return this;
    }
}
