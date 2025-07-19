package com.example.whatsappWebSocket.Controller;

import com.example.whatsappWebSocket.Service.GroupsService;
import com.example.whatsappWebSocket.template.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
public class GroupsController {

    private final GroupsService groupsService;

    @Autowired
    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

//    @GetMapping
//    public List<Groups> getAllGroups() {
//
//        return groupsService.getAllGroups();
//    }
@GetMapping("/{username}")
public List<Groups> getGroupsByUsername(@PathVariable String username) {
    List<Groups> allGroups = groupsService.getAllGroups();
    return allGroups.stream()
            .filter(group -> group.getKullanıcılar().contains(username)) // Kullanıcı adı kontrolü
            .collect(Collectors.toList());
}

    @PostMapping("/new")
    public ResponseEntity<Groups> createNewGroup(@RequestBody Groups group) {
        groupsService.saveGroups(group);
        return ResponseEntity.ok(group);
    }
}