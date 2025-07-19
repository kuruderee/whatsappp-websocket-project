package com.example.whatsappWebSocket.Repository;

import com.example.whatsappWebSocket.template.GroupsMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupsMessageRepository extends JpaRepository<GroupsMessage,Integer> {
    List<GroupsMessage> findByGroupname(String groupname);
}
