package com.vysh.projectmanager.service;
import java.util.List; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vysh.projectmanager.model.User;
import com.vysh.projectmanager.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String username, String rawPassword) {
        String role = isAdminUser(username) ? "ROLE_ADMIN" : "ROLE_USER";

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .role(role)
                .enabled(true)
                .build();

        userRepository.save(user);
    }

    private boolean isAdminUser(String username) {
        return username.equalsIgnoreCase("vysh") ||
               username.equalsIgnoreCase("admin1") ||
               username.equalsIgnoreCase("admin2");
    }
    public List<User> getAllUsers() {
    return userRepository.findAll();
}

}
