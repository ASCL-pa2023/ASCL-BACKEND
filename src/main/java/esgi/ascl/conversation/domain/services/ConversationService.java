package esgi.ascl.conversation.domain.services;

import esgi.ascl.conversation.infrastructure.repositories.ConversationRepository;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }
}
