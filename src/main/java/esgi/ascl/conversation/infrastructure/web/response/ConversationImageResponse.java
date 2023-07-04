package esgi.ascl.conversation.infrastructure.web.response;


public class ConversationImageResponse {
    public Long id;
    public String filename;
    public Long conversationId;
    public byte[] file;

    public Long getId() {
        return id;
    }

    public ConversationImageResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public ConversationImageResponse setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public ConversationImageResponse setConversationId(Long conversationId) {
        this.conversationId = conversationId;
        return this;
    }

    public byte[] getFile() {
        return file;
    }

    public ConversationImageResponse setFile(byte[] file) {
        this.file = file;
        return this;
    }
}
