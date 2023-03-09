package esgi.ascl.conversation.infrastructure.web.response;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.conversation.domain.entities.ConversationEntity;

import java.util.Date;

public class MessageResponse {
    public Long id;
    public Long userId;
    public Long conversationId;
    public String content;
    public String imageUrl;
    public Date creationDate;

    public Long getId() {
        return id;
    }
    public MessageResponse setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getUserId() {
        return userId;
    }

    public MessageResponse setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public MessageResponse setConversationId(Long conversationId) {
        this.conversationId = conversationId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public MessageResponse setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public MessageResponse setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
