package com.example.whatsappWebSocket.Jwt2;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class ProtectedResourceController {

    @GetMapping("/protected")
    @Secured("ROLE_USER")
    public String protectedResource() {
        return "bu korunaklı bir kaynaktır";
    }
    public UsernamePasswordAuthenticationToken createAuthenticationToken(String username, String password) {
        // Yetkileri belirle (örneğin ROLE_USER)
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }
}
