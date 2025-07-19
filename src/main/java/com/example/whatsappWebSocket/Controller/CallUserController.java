package com.example.whatsappWebSocket.Controller;

import com.example.whatsappWebSocket.Service.UserService;
import com.example.whatsappWebSocket.template.Call;
import com.example.whatsappWebSocket.template.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/userss")
public class CallUserController {
    private final UserService userService;

    @Autowired
    public CallUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> users = userService.getAllUsers(); // UserService, users tablosundaki kullanıcıları alır
        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        return ResponseEntity.ok(response);  // users yerine başka bilgi döndürülebilir
    }
}