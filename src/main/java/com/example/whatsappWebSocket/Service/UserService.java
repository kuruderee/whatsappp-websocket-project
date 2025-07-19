package com.example.whatsappWebSocket.Service;


import com.example.whatsappWebSocket.Repository.UserRepository;
import com.example.whatsappWebSocket.template.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public User findById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null); // Eğer kullanıcı bulunamazsa null döner
    }





    public List<User> getAllUsers() {
        return userRepository.findAll();
    }





    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public boolean authenticateUser(String username, String password) {

        User user = userRepository.findByUsername(username);
        if (user != null) {
            System.out.println("Kullanıcı adı: " + user.getUsername());
        } else {
            System.out.println("Kullanıcı bulunamadı.");
        }

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {

            return true; // Giriş başarılı
        }
        return false; // Giriş başarısız
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
