package esgi.ascl.news.infrastructure.web.responses;

import java.util.Date;

public class ConversationResponse {
    public Long id;
    public String title;
    public Long creatorId;
    public Date creationDate;

    public Long getId() {
        return id;
    }

    public ConversationResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ConversationResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public ConversationResponse setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public ConversationResponse setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
