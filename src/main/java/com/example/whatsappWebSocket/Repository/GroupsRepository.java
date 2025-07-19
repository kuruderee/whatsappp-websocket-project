package com.example.whatsappWebSocket.Repository;

import com.example.whatsappWebSocket.template.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, Integer> {
}
