package esgi.ascl.conversation.domain.services;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.conversation.domain.entities.ConversationEntity;
import esgi.ascl.conversation.infrastructure.repositories.ConversationRepository;
import esgi.ascl.conversation.infrastructure.web.requests.ConversationRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    public final UserConversationService userConversationService;
    private final MessageService messageService;

    public ConversationService(ConversationRepository conversationRepository, UserConversationService userConversationService, MessageService messageService) {
        this.conversationRepository = conversationRepository;
        this.userConversationService = userConversationService;
        this.messageService = messageService;
    }

    public ConversationEntity create(ConversationRequest conversationRequest) {
        var conversation = new ConversationEntity()
                .setTitle(conversationRequest.title)
                .setCreationDate(new Date());
        return conversationRepository.save(conversation);
    }

    public List<ConversationEntity> getAll() {
        return conversationRepository.findAll();
    }

    public ConversationEntity getById(Long id) {
        return conversationRepository.findById(id).orElse(null);
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

    public void deleteById(Long id) {
        messageService.deleteAllByConversationId(id);
        conversationRepository.deleteById(id);
    }

}
