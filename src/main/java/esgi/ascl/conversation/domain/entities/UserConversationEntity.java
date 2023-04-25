package esgi.ascl.conversation.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import esgi.ascl.User.domain.entities.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_conversation")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserConversationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversation;


    public Long getId() {
        return id;
    }
    public UserConversationEntity setId(Long id) {
        this.id = id;
        return this;
    }
    public User getUser() {
        return user;
    }
    public UserConversationEntity setUser(User user) {
        this.user = user;
        return this;
    }
    public ConversationEntity getConversation() {
        return conversation;
    }
    public UserConversationEntity setConversation(ConversationEntity conversation) {
        this.conversation = conversation;
        return this;
    }
}
