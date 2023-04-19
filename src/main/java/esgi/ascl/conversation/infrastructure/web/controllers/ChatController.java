package esgi.ascl.conversation.infrastructure.web.controllers;

import esgi.ascl.conversation.domain.services.MessageService;
import esgi.ascl.conversation.infrastructure.web.requests.MessageRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
public class ChatController {

    private final SimpMessagingTemplate template;
    private final MessageService messageService;

    public ChatController(SimpMessagingTemplate template, MessageService messageService) {
        this.template = template;
        this.messageService = messageService;
    }

    @MessageMapping("/messages")
    @SendTo("/channel/handle")
    public void handleMessage(MessageRequest messageRequest) {
        System.out.println("Message received !");
        System.out.println("messageRequest.conversationId: " + messageRequest.conversationId);
        System.out.println("messageRequest.userId: " + messageRequest.userId);
        System.out.println("messageRequest.content: " + messageRequest.content);
        System.out.println("messageRequest.imageUrl: " + messageRequest.imageUrl);
        //messageService.create(messageRequest);
        this.template.convertAndSend("/channel/conversation/" + messageRequest.conversationId, messageRequest);
    }
}
