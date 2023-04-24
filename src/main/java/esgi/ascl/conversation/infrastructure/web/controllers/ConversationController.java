package esgi.ascl.conversation.infrastructure.web.controllers;

import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.conversation.domain.services.ConversationService;
import esgi.ascl.conversation.domain.services.UserConversationService;
import esgi.ascl.conversation.infrastructure.web.requests.ConversationRequest;
import esgi.ascl.news.domain.mapper.ConversationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/conversation")
public class ConversationController {
    private final ConversationService conversationService;
    private final UserService userService;
    private final UserConversationService userConversationService;

    public ConversationController(ConversationService conversationService, UserService userService, UserConversationService userConversationService) {
        this.conversationService = conversationService;
        this.userService = userService;
        this.userConversationService = userConversationService;
    }

    /**
     * Create a conversation
     * @param conversationRequest : conversation request
     */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ConversationRequest conversationRequest) {
        var conversationAlreadyExist = conversationService.getByTitle(conversationRequest);
        if(conversationAlreadyExist != null) {
            return new ResponseEntity<>("Conversation with name <" + conversationRequest.title + "> already exist", HttpStatus.BAD_REQUEST);
        }
        var conversation = conversationService.create(conversationRequest);
        return new ResponseEntity<>(conversation, HttpStatus.CREATED);
    }

    /**
     * Get all conversations
     * @return List<ConversationResponse>
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        var conversations = conversationService.getAll()
                .stream()
                .map(ConversationMapper::entityToResponse)
                .collect(toList());
        return new ResponseEntity<>(conversations, HttpStatus.OK);
    }

    /**
     * Get a conversation by id
     * @param id : id of the conversation
     * @return List of conversation entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        var conversation = conversationService.getById(id);
        if(conversation == null) {
            return new ResponseEntity<>("Conversation not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ConversationMapper.entityToResponse(conversation), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        var conversation = conversationService.getById(id);
        if(conversation == null) {
            return new ResponseEntity<>("Conversation not found", HttpStatus.NOT_FOUND);
        }
        conversationService.deleteById(id);
        return new ResponseEntity<>("Conversation deleted", HttpStatus.OK);
    }

    /**
     * Add a person to a conversation
     * @param conversationId : id of the conversation
     * @param userEmail : email of the user to add
     */
    @PostMapping("/{conversationId}/addPerson/{userEmail}")
    public ResponseEntity<?> addPerson(@PathVariable Long conversationId, @PathVariable String userEmail){
        var conversation = conversationService.getById(conversationId);
        if(conversation == null) return new ResponseEntity<>("Conversation not found", HttpStatus.NOT_FOUND);

        var user = userService.getByEmail(userEmail);
        if(user.isEmpty()) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        var alreadyInConversation = userConversationService.getByConversationIdAndUserId(conversationId, user.get().getId());
        if (alreadyInConversation != null) return new ResponseEntity<>("User already in conversation", HttpStatus.BAD_REQUEST);

        userConversationService.addPerson(conversation, user.get());

        return new ResponseEntity<>("Person added to conversation", HttpStatus.OK);
    }

    /**
     * Delete a person from a conversation
     * @param conversationId : id of the conversation
     * @param userEmail : email of the user to delete
     */
    @DeleteMapping("/{conversationId}/deletePerson/{userEmail}")
    public ResponseEntity<?> deletePerson(@PathVariable Long conversationId, @PathVariable String userEmail){
        var conversation = conversationService.getById(conversationId);
        if(conversation == null) return new ResponseEntity<>("Conversation not found", HttpStatus.NOT_FOUND);

        var user = userService.getByEmail(userEmail);
        if(user.isEmpty()) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        var alreadyInConversation = userConversationService.getByConversationIdAndUserId(conversationId, user.get().getId());
        if (alreadyInConversation == null) return new ResponseEntity<>("User not in conversation", HttpStatus.BAD_REQUEST);

        userConversationService.deletePerson(conversation.getId(), user.get().getId());

        return new ResponseEntity<>("Person deleted from conversation", HttpStatus.OK);
    }

    /**
     * Get all conversations of a user
     * @param userId : id of the user
     * @return List of conversation entity
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllUserConversation(@PathVariable Long userId){
        var user = userService.getById(userId);
        if(user == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        var conversations = conversationService.getAllByUserId(userId)
                .stream()
                .map(ConversationMapper::entityToResponse)
                .collect(toList());

        return new ResponseEntity<>(conversations, HttpStatus.OK);
    }

    /**
     * Get all persons of a conversation
     * @param conversationId : id of the conversation
     * @return List of user
     */
    @GetMapping("/{conversationId}/persons")
    public ResponseEntity<?> getAllPersonsConversation(@PathVariable Long conversationId){
        var conversation = conversationService.getById(conversationId);
        if(conversation == null) return new ResponseEntity<>("Conversation not found", HttpStatus.NOT_FOUND);

        var persons = conversationService.getAllUserInConversation(conversationId);
        //TODO : map to user response
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }
}
