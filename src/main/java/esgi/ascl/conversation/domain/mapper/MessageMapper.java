package esgi.ascl.conversation.domain.mapper;

import esgi.ascl.conversation.domain.entities.MessageEntity;
import esgi.ascl.conversation.infrastructure.web.response.MessageResponse;

public class MessageMapper {

    public static MessageResponse entityToResponse(MessageEntity messageEntity) {
        return new MessageResponse()
                .setId(messageEntity.getId())
                .setUserId(messageEntity.getUser().getId())
                .setConversationId(messageEntity.getConversation().getId())
                .setContent(messageEntity.getContent())
                .setImageUrl(messageEntity.getImageUrl())
                .setCreationDate(messageEntity.getCreationDate());
    }
}
