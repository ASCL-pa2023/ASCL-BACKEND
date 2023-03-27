package esgi.ascl.conversation.infrastructure.web.controllers;

import esgi.ascl.conversation.domain.mapper.MessageMapper;
import esgi.ascl.conversation.domain.services.ConversationService;
import esgi.ascl.conversation.domain.services.MessageService;
import esgi.ascl.conversation.infrastructure.web.requests.MessageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;
    private final ConversationService conversationService;

    public MessageController(MessageService messageService, ConversationService conversationService) {
        this.messageService = messageService;
        this.conversationService = conversationService;
    }

    /**
     * Create a message
     * @param messageRequest
     */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody MessageRequest messageRequest) {
        var conversation = conversationService.getById(messageRequest.conversationId);
        if (conversation == null) return new ResponseEntity<>("Conversation not found", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(messageService.create(messageRequest, conversation));
    }

    /**
     * Get a message
     * @param messageId : id of the conversation
     * @return message response
     */
    @GetMapping("/{messageId}")
    public ResponseEntity<?> getById(@PathVariable Long messageId) {
        var message = messageService.getById(messageId);
        if(message == null) return new ResponseEntity<>("Message not found", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(MessageMapper.entityToResponse(message));
    }

    /**
     * Get all messages by conversation id
     * @param conversationId : id of the conversation
     * @return list of messages
     */
    @GetMapping("/all/{conversationId}")
    public ResponseEntity<?> getAllByConversationId(@PathVariable Long conversationId) {
        var conversation = conversationService.getById(conversationId);
        if (conversation == null) return new ResponseEntity<>("Conversation not found", HttpStatus.NOT_FOUND);

        var messages = messageService.getAllByConversationId(conversationId)
                .stream()
                .map(MessageMapper::entityToResponse)
                .toList();
        return ResponseEntity.ok(messages);
    }

    /**
     * Get last message by conversation id
     * @param conversationId : id of the conversation
     * @return message response
     */
    @GetMapping("/last/{conversationId}")
    public ResponseEntity<?> getLastMessageByConversationId(@PathVariable Long conversationId) {
        var conversation = conversationService.getById(conversationId);
        if (conversation == null) return new ResponseEntity<>("Conversation not found", HttpStatus.NOT_FOUND);

        var message = messageService.getLastMessageByConversationId(conversationId);

        return ResponseEntity.ok(MessageMapper.entityToResponse(message));
    }

    /**
     * Delete a message
     * @param id : id of the message
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        var message = messageService.getById(id);
        if(message == null) return new ResponseEntity<>("Message not found", HttpStatus.NOT_FOUND);

        messageService.delete(message);
        return ResponseEntity.ok("Message deleted");
    }

    /**
     * Delete all messages by conversation id
     * @param conversationId : id of the conversation
     */
    @DeleteMapping("/delete/all/{conversationId}")
    public ResponseEntity<?> deleteAllByConversationId(@PathVariable Long conversationId) {
        var conversation = conversationService.getById(conversationId);
        if (conversation == null) return new ResponseEntity<>("Conversation not found", HttpStatus.NOT_FOUND);

        messageService.removeAllByConversationId(conversationId);
        return ResponseEntity.ok("Messages deleted");
    }

}
