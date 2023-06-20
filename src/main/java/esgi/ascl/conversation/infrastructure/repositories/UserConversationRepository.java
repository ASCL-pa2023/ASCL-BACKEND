package esgi.ascl.conversation.infrastructure.repositories;

import esgi.ascl.conversation.domain.entities.UserConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserConversationRepository extends JpaRepository<UserConversationEntity, Long> {
    List<UserConversationEntity> findAllByConversationId(Long conversationId);
    UserConversationEntity findByConversationIdAndAndUserId(Long conversationId, Long userId);
    List<UserConversationEntity> findAllByUserId(Long userId);
}
