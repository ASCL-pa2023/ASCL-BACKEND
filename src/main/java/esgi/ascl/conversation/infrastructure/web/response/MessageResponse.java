package esgi.ascl.conversation.infrastructure.web.response;

import esgi.ascl.User.Entitie.User;
import esgi.ascl.conversation.domain.entities.ConversationEntity;

import java.util.Date;

public class MessageResponse {
    public User user;
    public ConversationEntity conversation;
    public String content;
    public String imageUrl;
    public Date creationDate;

    public User getUser() {
        return user;
    }

    public MessageResponse setUser(User user) {
        this.user = user;
        return this;
    }

    public ConversationEntity getConversation() {
        return conversation;
    }

    public MessageResponse setConversation(ConversationEntity conversation) {
        this.conversation = conversation;
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
