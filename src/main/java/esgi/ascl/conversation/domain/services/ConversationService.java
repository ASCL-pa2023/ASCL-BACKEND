package esgi.ascl.conversation.domain.services;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.infrastructure.repositories.UserRepository;
import esgi.ascl.conversation.domain.entities.ConversationEntity;
import esgi.ascl.conversation.domain.exeptions.ConversationException;
import esgi.ascl.conversation.infrastructure.repositories.ConversationRepository;
import esgi.ascl.conversation.infrastructure.repositories.MessageRepository;
import esgi.ascl.conversation.infrastructure.web.requests.ConversationRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    public final UserConversationService userConversationService;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ConversationService(ConversationRepository conversationRepository, UserConversationService userConversationService, MessageRepository messageRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userConversationService = userConversationService;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public ConversationEntity create(ConversationRequest conversationRequest) {
        var conversation = new ConversationEntity()
                .setTitle(conversationRequest.title)
                .setCreatorId(conversationRequest.creatorId)
                .setCreationDate(new Date());


        userConversationService.addPerson(
                conversation,
                userRepository.getUserById(conversationRequest.creatorId)
        );

        return conversationRepository.save(conversation);
    }

    public List<ConversationEntity> getAll() {
        return conversationRepository.findAll();
    }

    public ConversationEntity getById(Long id) {
        return conversationRepository.findById(id)
                .orElseThrow(() -> new ConversationException("Conversation not found"));
    }

    public ConversationEntity getByTitle(ConversationRequest conversationRequest) {
        return conversationRepository.findByTitle(conversationRequest.title);
    }

    public List<ConversationEntity> getAllByUserId(Long userId) {
        var userConversations = userConversationService.getAllByUserId(userId);

        var res = new ArrayList<ConversationEntity>();
        userConversations.forEach(userConversation -> {
            res.add(userConversation.getConversation());
        });
        return res;
    }

    public List<User> getAllUserInConversation(Long conversationId){
        var userConversations = userConversationService.getAllByConversationId(conversationId);
        var res = new ArrayList<User>();
        userConversations.forEach(userConversation -> {
            res.add(userConversation.getUser());
        });
        return res;
    }

    @Transactional
    public void delete(ConversationEntity conversationEntity) {
        messageRepository
                .findAllByConversationId(conversationEntity.getId())
                .forEach(message -> {
                    message.setConversation(null);
                    message.setUser(null);
                    messageRepository.delete(message);
                });

        userConversationService.deleteAllByConversationId(conversationEntity.getId());
        conversationRepository.delete(conversationEntity);
    }

}
