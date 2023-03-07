package esgi.ascl.conversation.infrastructure.repositories;

import esgi.ascl.conversation.domain.entities.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, Long> {

    ConversationEntity findByTitle(String title);
}
