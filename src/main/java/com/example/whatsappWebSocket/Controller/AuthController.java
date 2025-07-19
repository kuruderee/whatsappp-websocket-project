package com.example.whatsappWebSocket.Controller;






import com.example.whatsappWebSocket.Jwt2.JwtUtil;
import com.example.whatsappWebSocket.Jwt2.ProtectedResourceController;
import com.example.whatsappWebSocket.Service.UserService;
import com.example.whatsappWebSocket.template.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProtectedResourceController protectedResourceController;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // register.html sayfasını döndür
    }

    @PostMapping("/api/register")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/login.html";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        boolean isAuthenticated = userService.authenticateUser(user.getUsername(), user.getPassword());

        if (isAuthenticated) {
            UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
            Authentication  authentication = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
            SecurityContext securityContext = SecurityContextHolder.getContext();

            securityContext.setAuthentication(authentication);
            System.out.println(securityContext.getAuthentication().getName());

            String token = com.example.whatsappWebSocket.Jwt2.JwtUtil.generateToken(user.getUsername());
            System.out.println("çalışıyor");
            System.out.println(token);
            String username = JwtUtil.extractUsername(token); // bunu sonra kullanacağız

            return ResponseEntity.ok().body(new TokenResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Giriş başarısız");
        }
    }


    public class TokenResponse {
        private String token;

        public TokenResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

}


