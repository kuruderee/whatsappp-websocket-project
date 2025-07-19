package com.example.whatsappWebSocket.Repository;

import com.example.whatsappWebSocket.template.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySenderAndGetter(String sender, String getter);
}
