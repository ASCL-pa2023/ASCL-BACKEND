package esgi.ascl.conversation.infrastructure.repositories;

import esgi.ascl.conversation.domain.entities.ConversationImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationImageRepository extends JpaRepository<ConversationImage, Long>{
    ConversationImage findByConversationId(Long conversationId);
}
