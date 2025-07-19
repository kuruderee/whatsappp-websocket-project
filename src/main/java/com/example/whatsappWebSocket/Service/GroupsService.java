package com.example.whatsappWebSocket.Service;

import com.example.whatsappWebSocket.Repository.GroupsRepository;
import com.example.whatsappWebSocket.template.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupsService {

    @Autowired
    private GroupsRepository groupsRepository;

    public Groups saveGroups(Groups groups) {
        return groupsRepository.save(groups);
    }
    public List<Groups> getAllGroups() {
        return groupsRepository.findAll();
    }
}
