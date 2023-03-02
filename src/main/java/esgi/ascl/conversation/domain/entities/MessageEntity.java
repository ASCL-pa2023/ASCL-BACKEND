package esgi.ascl.conversation.domain.entities;

import esgi.ascl.User.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "message")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversation;
    private String content;
    private String imageUrl;
    private Date creationDate;


    public Long getId() {
        return id;
    }

    public MessageEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public MessageEntity setUser(User user) {
        this.user = user;
        return this;
    }

    public ConversationEntity getConversation() {
        return conversation;
    }

    public MessageEntity setConversation(ConversationEntity conversation) {
        this.conversation = conversation;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public MessageEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public MessageEntity setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
