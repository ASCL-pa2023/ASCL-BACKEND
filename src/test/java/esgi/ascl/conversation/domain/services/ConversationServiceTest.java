package esgi.ascl.conversation.domain.services;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.conversation.domain.entities.ConversationEntity;
import esgi.ascl.conversation.infrastructure.repositories.ConversationRepository;
import esgi.ascl.conversation.infrastructure.web.requests.ConversationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversationServiceTest {
    ConversationRepository conversationRepository;
    UserConversationService userConversationService;
    MessageService messageService;
    ConversationService conversationService;

    ConversationEntity conversationEntity1 = new ConversationEntity();
    ConversationEntity conversationEntity2 = new ConversationEntity();
    ConversationEntity conversationEntity3 = new ConversationEntity();
    User user = new User();


    @BeforeEach
    void setUp() {
        conversationRepository = Mockito.mock(ConversationRepository.class);
        userConversationService = Mockito.mock(UserConversationService.class);
        messageService = Mockito.mock(MessageService.class);
        conversationService = new ConversationService(conversationRepository, userConversationService, messageService);

        conversationEntity1
                .setId(1L)
                .setTitle("conversation1")
                .setCreationDate(new Date());

        conversationEntity2
                .setId(2L)
                .setTitle("conversation2")
                .setCreationDate(new Date());

        conversationEntity3
                .setId(3L)
                .setTitle("conversation3")
                .setCreationDate(new Date());

        user
            .setId(Long.parseLong("1"))
            .setEmail("email")
            .setPhone("0693759302")
            .setFirstname("firstName")
            .setLastname("lastName")
            .setPassword("password")
            .setLicense("license")
            .setBirthday(new Date())
            .setProfilePicture("profilePicture");
    }

    @Test
    void should_get_all_conversations() {
        var conversationList = List.of(conversationEntity1, conversationEntity2, conversationEntity3);

        when(conversationRepository.findAll()).thenReturn(conversationList);

        var result = conversationService.getAll();
        assertEquals(result, conversationList);
    }

    @Test
    void should_get_conversation_by_id() {
        when(conversationRepository.findById(1L)).thenReturn(java.util.Optional.of(conversationEntity1));

        var result = conversationService.getById(1L);
        assertEquals(result, conversationEntity1);
    }

    @Test
    void should_get_conversation_by_title() {
        when(conversationRepository.findByTitle("conversation2"))
                .thenReturn(conversationEntity2);

        var conversationRequest = new ConversationRequest().setTitle("conversation2");
        var result = conversationService.getByTitle(conversationRequest);
        assertEquals(result, conversationEntity2);
    }

}