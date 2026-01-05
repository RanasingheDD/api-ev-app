package com.ev_booking_system.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ev_booking_system.api.dto.EvDto;
import com.ev_booking_system.api.dto.UserDto;
import com.ev_booking_system.api.filter.JwtUtil;
import com.ev_booking_system.api.mapper.EvMapper;
import com.ev_booking_system.api.repository.EvRepository;
import com.ev_booking_system.api.model.UserModel;
import com.ev_booking_system.api.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EvRepository evRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<?> register(UserModel user){
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Register successfully");
    }
    
    public UserDto getCurrentUserFromToken(String token) {
    try {

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String userId = jwtUtil.extractUserId(token); 

        UserModel user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setMobile(user.getMobile());
        dto.setRole(user.getRole());
        dto.setPoints(user.getPoints());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;   // <-- return SINGLE object

    } catch (Exception e) {
        e.printStackTrace();   // <--- print exception!
        return null;
    }
}


    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserDto dto = new UserDto();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public UserModel getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<EvDto> getUserEv(String token){
        try {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String userId = jwtUtil.extractUserId(token);
        System.out.println(userId);
        return evRepository.findByUserId(userId).stream()
                .map(EvMapper::toDto).collect(Collectors.toList());

    } catch (Exception e) {
        e.printStackTrace();   // <--- print exception!
        return Collections.emptyList();
    }
}

    public UserDto updateUser(String name, String phone, String token) {

    try {
        // Validate Bearer token
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new RuntimeException("Invalid token format");
        }

        // Extract user ID
        String userId = jwtUtil.extractUserId(token);

        // Fetch user
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Apply updates
        if (name != null && !name.isEmpty()) {
            user.setName(name);
        }

        if (phone != null && !phone.isEmpty()) {
            user.setMobile(phone);
        }

        // Save user
        UserModel saved = userRepository.save(user);

        // Convert to DTO
        UserDto dto = new UserDto();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setMobile(saved.getMobile());

        return dto;

    } catch (Exception e) {
        throw new RuntimeException("Profile update failed: " + e.getMessage());
    }
}

}

