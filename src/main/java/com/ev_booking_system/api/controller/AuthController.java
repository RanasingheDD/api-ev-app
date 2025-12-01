package com.ev_booking_system.api.controller;

import com.ev_booking_system.api.dto.LoginRequest;
import com.ev_booking_system.api.model.UserModel;
import com.ev_booking_system.api.repository.UserRepository;
import com.ev_booking_system.api.filter.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        userRepository.save(user);
        String accessToken = jwtUtil.generateAccessToken(user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        UserModel user = userRepository.findByEmail(req.getEmail());

        if (user == null || !user.getPassword().equals(req.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }
}

class AuthResponse {
    public String accessToken;
    public String refreshToken;

    public AuthResponse(String a, String r) {
        this.accessToken = a;
        this.refreshToken = r;
    }
}
