package esgi.ascl.conversation.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "conversation_image")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ConversationImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "consersation_id")
    private ConversationEntity conversation;
    private String filename;
    private String url;
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public ConversationEntity getConversation() {
        return conversation;
    }

    public ConversationImage setConversation(ConversationEntity conversation) {
        this.conversation = conversation;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public ConversationImage setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ConversationImage setUrl(String url) {
        this.url = url;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public ConversationImage setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

}
