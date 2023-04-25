package esgi.ascl.conversation.infrastructure.web.requests;

public class AddPersonRequest {
    public Long conversationId;
    public String userEmail;
    public Long conversationCreatorId;


    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getConversationCreatorId() {
        return conversationCreatorId;
    }

    public void setConversationCreatorId(Long conversationCreatorId) {
        this.conversationCreatorId = conversationCreatorId;
    }
}


