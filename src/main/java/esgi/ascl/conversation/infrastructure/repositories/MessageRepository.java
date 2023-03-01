package esgi.ascl.conversation.infrastructure.repositories;

import esgi.ascl.conversation.domain.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
}
