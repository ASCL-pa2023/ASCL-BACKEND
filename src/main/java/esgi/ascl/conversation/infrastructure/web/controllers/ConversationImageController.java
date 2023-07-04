package esgi.ascl.conversation.infrastructure.web.controllers;

import esgi.ascl.conversation.domain.services.ConversationImageService;
import esgi.ascl.conversation.domain.services.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/conversation/image")
public class ConversationImageController {
    private final ConversationService conversationService;
    private final ConversationImageService conversationImageService;

    public ConversationImageController(ConversationService conversationService, ConversationImageService conversationImageService) {
        this.conversationService = conversationService;
        this.conversationImageService = conversationImageService;
    }

    @PostMapping("/upload/{conversationId}")
    public ResponseEntity<?> uploadFile(@RequestParam("image") MultipartFile file,
                                        @PathVariable Long conversationId) {
        try {
            conversationService.getById(conversationId);
            conversationImageService.uploadImage(conversationId, file);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/download/{conversationId}")
    public ResponseEntity<?> downloadFile(@PathVariable Long conversationId) {
        try {
            conversationService.getById(conversationId);
            var file = conversationImageService.getByConversationId(conversationId);
            if(file == null) return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(file, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{conversationId}")
    public ResponseEntity<?> deleteFile(@PathVariable Long conversationId) {
        try {
            conversationImageService.deleteByConversationId(conversationId);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }
}
