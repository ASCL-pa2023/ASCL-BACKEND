package esgi.ascl.conversation.infrastructure.repositories;

import esgi.ascl.conversation.domain.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findAllByConversationId(Long conversationId);

    @Query("SELECT m " +
            "FROM MessageEntity m " +
            "where m.conversation.id = :conversationId " +
            "AND m.creationDate = (SELECT MAX(m2.creationDate) " +
                                    "FROM MessageEntity m2 " +
                                    "where m2.conversation.id = :conversationId)")
    MessageEntity findLastMessageByConversationId(@Param("conversationId") Long conversationId);
    void deleteAllByConversationId(Long conversationId);
}
