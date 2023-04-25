package esgi.ascl.news.domain.mapper;

import esgi.ascl.conversation.domain.entities.ConversationEntity;
import esgi.ascl.news.infrastructure.web.responses.ConversationResponse;

public class ConversationMapper {


    public static ConversationResponse entityToResponse(ConversationEntity conversationEntity) {
        return new ConversationResponse()
                .setId(conversationEntity.getId())
                .setTitle(conversationEntity.getTitle())
                .setCreatorId(conversationEntity.getCreatorId())
                .setCreationDate(conversationEntity.getCreationDate());
    }
}
