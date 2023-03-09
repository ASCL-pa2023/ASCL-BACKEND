package esgi.ascl.conversation.domain.services;

import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.conversation.domain.entities.ConversationEntity;
import esgi.ascl.conversation.domain.entities.MessageEntity;
import esgi.ascl.conversation.infrastructure.repositories.MessageRepository;
import esgi.ascl.conversation.infrastructure.web.requests.MessageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public MessageEntity create(MessageRequest messageRequest, ConversationEntity conversation) {
        var user = userService.getById(messageRequest.userId);

        var message = new MessageEntity()
                .setUser(user)
                .setConversation(conversation)
                .setContent(messageRequest.content)
                .setImageUrl(messageRequest.imageUrl)
                .setCreationDate(new Date());
        return messageRepository.save(message);
    }

    public MessageEntity getById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public List<MessageEntity> getAllByConversationId(Long conversationId) {
        return messageRepository.findAllByConversationId(conversationId);
    }

    public MessageEntity getLastMessageByConversationId(Long conversationId) {
        return messageRepository.findLastMessageByConversationId(conversationId);
    }

    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

    public void deleteAllByConversationId(Long conversationId) {
        messageRepository.deleteAllByConversationId(conversationId);
    }

}
