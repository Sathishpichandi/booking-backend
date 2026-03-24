package com.booking.bookingplatform.service;

import com.booking.bookingplatform.entity.User;
import com.booking.bookingplatform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // NO PasswordEncoder for now (as you requested)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ================= REGISTER =================
    public User register(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        user.setRole("USER"); // default role
        return userRepository.save(user);
    }


    // ================= LOGIN =================
    public User loginUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password)) // no encryption for now
                .orElse(null);
    }


}
