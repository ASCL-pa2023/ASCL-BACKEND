package esgi.ascl.conversation.domain.mapper;

import esgi.ascl.conversation.domain.entities.ConversationEntity;
import esgi.ascl.conversation.domain.entities.ConversationImage;
import esgi.ascl.conversation.infrastructure.web.requests.ConversationImageRequest;
import esgi.ascl.conversation.infrastructure.web.response.ConversationImageResponse;

import java.util.Date;

public class ConversationImageMapper {
    public static ConversationImage toEntity(ConversationImageRequest conversationImageRequest, ConversationEntity conversationEntity) {
        return new ConversationImage()
                .setConversation(conversationEntity)
                .setFilename(conversationImageRequest.getFilename())
                .setFilename(conversationImageRequest.getFilename())
                .setUrl(conversationImageRequest.getUrl())
                .setCreationDate(new Date());
    }

    public static ConversationImageResponse toEntity(ConversationImage conversationImage, byte[] file) {
        return new ConversationImageResponse()
                .setId(conversationImage.getId())
                .setFilename(conversationImage.getFilename())
                .setConversationId(conversationImage.getConversation().getId())
                .setFilename(conversationImage.getFilename())
                .setFile(file);
    }
}
