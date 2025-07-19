package com.example.whatsappWebSocket.Service;

import com.example.whatsappWebSocket.Repository.MessageRepository;
import com.example.whatsappWebSocket.template.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {

        return messageRepository.save(message);
    }
    public List<Message> getMessagesBetween(String sender, String getter) {
        List<Message> messages = messageRepository.findBySenderAndGetter(sender, getter);
        List<Message> messages1 = messageRepository.findBySenderAndGetter(getter,sender);

        messages.addAll(messages1);
        messages.sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));
        return messages;

    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
