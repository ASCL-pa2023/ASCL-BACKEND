package esgi.ascl.conversation.domain.services;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.conversation.domain.entities.ConversationEntity;
import esgi.ascl.conversation.domain.entities.MessageEntity;
import esgi.ascl.conversation.infrastructure.repositories.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    MessageRepository messageRepository;
    MessageService messageService;
    UserService userService;
    MessageEntity messageEntity1 = new MessageEntity();
    MessageEntity messageEntity2 = new MessageEntity();
    MessageEntity messageEntity3 = new MessageEntity();
    ConversationEntity conversationEntity = new ConversationEntity();
    User user = new User();

    @BeforeEach
    void setUp() throws ParseException {
        messageRepository = Mockito.mock(MessageRepository.class);
        userService = Mockito.mock(UserService.class);
        messageService = new MessageService(messageRepository, userService);

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

        conversationEntity
                .setId(1L)
                .setTitle("title")
                .setCreationDate(new Date());

        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-17");
        messageEntity1
                .setId(1L)
                .setUser(user)
                .setConversation(conversationEntity)
                .setContent("content1")
                .setImageUrl("imageUrl1")
                .setCreationDate(date1);

        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-18");
        messageEntity2
                .setId(2L)
                .setUser(user)
                .setConversation(conversationEntity)
                .setContent("content2")
                .setImageUrl("imageUrl2")
                .setCreationDate(date2);

        Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-19");
        messageEntity3
                .setId(3L)
                .setUser(user)
                .setConversation(conversationEntity)
                .setContent("content3")
                .setImageUrl("imageUrl3")
                .setCreationDate(date3);
    }

    @Test
    void should_get_message_by_id() {
        when(messageRepository.findById(1L)).thenReturn(Optional.of(messageEntity1));
        MessageEntity messageEntity = messageService.getById(1L);
        assertEquals(messageEntity1, messageEntity);
    }

    @Test
    void should_get_all_messages_by_conversation_id() {
        var messageList = List.of(messageEntity1, messageEntity2, messageEntity3);

        when(messageRepository.findAllByConversationId(1L))
                .thenReturn(messageList);

        List<MessageEntity> messageEntities = messageService.getAllByConversationId(1L);

        assertEquals(messageList, messageEntities);
    }

    @Test
    void should_get_last_message_of_a_conversation() {
        when(messageRepository.findLastMessageByConversationId(conversationEntity.getId()))
                .thenReturn(messageEntity3);

        var messageEntity = messageService.getLastMessageByConversationId(conversationEntity.getId());
        assertEquals(messageEntity3, messageEntity);
    }

}