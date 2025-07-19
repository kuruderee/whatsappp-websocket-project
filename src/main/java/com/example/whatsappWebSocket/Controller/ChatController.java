package com.example.whatsappWebSocket.Controller;

import com.example.whatsappWebSocket.DTO.ChatMessage;
import com.example.whatsappWebSocket.Service.MessageService;
import com.example.whatsappWebSocket.template.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/public")
    public ChatMessage newUser(ChatMessage chatMessage) {
        chatMessage.setContent("Yeni kullanıcı katıldı: " + chatMessage.getSender());
        return chatMessage;
    }
    @GetMapping("/{sender}/{getter}")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(@PathVariable String sender, @PathVariable String getter) {
        List<Message> messages = messageService.getMessagesBetween(sender, getter);
        return ResponseEntity.ok(messages);
    }
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@RequestBody ChatMessage chatMessage) {
    Message message = new Message();
        message.setSender(chatMessage.getSender());
        message.setGetter(chatMessage.getGetter());
        message.setContent(chatMessage.getContent());
        message.setTimestamp(LocalDateTime.now());

        messageService.saveMessage(message);

    }

}
