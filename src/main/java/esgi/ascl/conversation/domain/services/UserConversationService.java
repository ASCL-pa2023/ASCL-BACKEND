package esgi.ascl.conversation.domain.services;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.conversation.domain.entities.ConversationEntity;
import esgi.ascl.conversation.domain.entities.UserConversationEntity;
import esgi.ascl.conversation.infrastructure.repositories.UserConversationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserConversationService {

    private final UserConversationRepository userConversationRepository;

    public UserConversationService(UserConversationRepository userConversationRepository) {
        this.userConversationRepository = userConversationRepository;
    }

    public UserConversationEntity addPerson(ConversationEntity conversationEntity, User user) {
        var userConversation = new UserConversationEntity()
                .setUser(user)
                .setConversation(conversationEntity);
        return userConversationRepository.save(userConversation);
    }

    public List<UserConversationEntity> getAllByConversationId(Long conversationId) {
        return userConversationRepository.findAllByConversationId(conversationId);
    }

    public List<UserConversationEntity> getAllByUserId(Long userId) {
        return userConversationRepository.findAllByUserId(userId);
    }
    public UserConversationEntity getByConversationIdAndUserId(Long conversationId, Long userId) {
        return userConversationRepository.findByConversationIdAndAndUserId(conversationId, userId);
    }

    @Transactional
    public void deletePerson(Long conversationId, Long userId) {
        var userConversation = getByConversationIdAndUserId(conversationId, userId)
                .setUser(null).setConversation(null);
        userConversationRepository.save(userConversation);

        userConversationRepository.deleteById(userConversation.getId());
    }

    @Transactional
    public void deleteAllByConversationId(Long conversationId) {
        var userConversations = userConversationRepository.findAllByConversationId(conversationId);

        userConversations.forEach(userConversation -> {
            userConversation.setConversation(null).setUser(null);
            userConversationRepository.delete(userConversation);
        });
    }

}
