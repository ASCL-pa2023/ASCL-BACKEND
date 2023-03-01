package esgi.ascl.conversation.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "conversation")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ConversationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public ConversationEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ConversationEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public ConversationEntity setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
