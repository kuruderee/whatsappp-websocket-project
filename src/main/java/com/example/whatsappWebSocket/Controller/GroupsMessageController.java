package com.example.whatsappWebSocket.Controller;

import com.example.whatsappWebSocket.Service.GroupsMessageService;
import com.example.whatsappWebSocket.template.GroupsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupsMessageController {

    private final GroupsMessageService groupsMessageService;

    @Autowired
    public GroupsMessageController(GroupsMessageService groupsMessageService) {
        this.groupsMessageService = groupsMessageService;
    }

    @GetMapping("/{groupname}/messages")
    public ResponseEntity<List<GroupsMessage>> getGroupMessages(@PathVariable String groupname) {

        List<GroupsMessage> groupMessages = groupsMessageService.getGroupsMessages(groupname);
        return ResponseEntity.ok(groupMessages);
    }

    @MessageMapping("/chat.sendGroupMessage")
    @SendTo("/topic/{groupname}")
    public GroupsMessage sendGroupMessage(@DestinationVariable("groupname")String groupname, @RequestBody GroupsMessage groupMessage) {
        groupsMessageService.saveMessage(groupMessage);
        return groupMessage;
    }

}
