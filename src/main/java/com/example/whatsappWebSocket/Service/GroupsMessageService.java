package com.example.whatsappWebSocket.Service;

import com.example.whatsappWebSocket.Repository.GroupsMessageRepository;
import com.example.whatsappWebSocket.template.GroupsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupsMessageService {
    @Autowired
    private GroupsMessageRepository groupsMessageRepository;

    public GroupsMessage saveMessage (GroupsMessage groupsMessage) {
        return groupsMessageRepository.save(groupsMessage);
    }
    public List<GroupsMessage> getGroupsMessages(String groupname) {
        return groupsMessageRepository.findByGroupname(groupname);

    }
}
