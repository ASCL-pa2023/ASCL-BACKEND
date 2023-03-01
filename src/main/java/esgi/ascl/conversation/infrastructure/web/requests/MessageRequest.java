package esgi.ascl.conversation.infrastructure.web.requests;


public class MessageRequest {
    public Long userId;
    public Long conversationId;
    public String content;
    public String imageUrl;

    public Long getUserId() {
        return userId;
    }

    public MessageRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public MessageRequest setConversationId(Long conversationId) {
        this.conversationId = conversationId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageRequest setContent(String content) {
        this.content = content;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public MessageRequest setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

}
