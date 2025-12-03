package com.ev_booking_system.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ev_booking_system.api.dto.UserDto;
import com.ev_booking_system.api.dto.EvDto;
import com.ev_booking_system.api.dto.UpdateProfileRequest;
import com.ev_booking_system.api.model.EvModel;
import com.ev_booking_system.api.service.EvService;
import com.ev_booking_system.api.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EvService evService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    public UserDto getCurrentUser(@RequestHeader("Authorization") String token){
        return userService.getCurrentUserFromToken(token);
    }
    
    @PutMapping("/me")
    public ResponseEntity<UserDto> updateProfile(
            @RequestBody UpdateProfileRequest request,
            @RequestHeader("Authorization") String token
    ) {
        UserDto updated = userService.updateUser(
                request.getName(),
                request.getPhone(),
                token
        );

        return ResponseEntity.ok(updated);
    }

    @PostMapping("/evs")
    public ResponseEntity<?> addEV(@RequestBody EvModel evModel ,@RequestHeader("Authorization") String token) {
        EvModel evs =  evService.addEV(evModel, token);
        return ResponseEntity.ok(Map.of("evs",List.of(evs))); 
    }

    @GetMapping("/evs")
    public ResponseEntity<?> getUserEv(@RequestHeader("Authorization") String token){
        List<EvDto> evs =  userService.getUserEv(token);
        return ResponseEntity.ok(Map.of("evs", evs));
    }

    @PutMapping("/evs/{id}")
    public EvDto updateEv(@RequestBody EvDto evDto, @PathVariable String id){
        return evService.updateEv(evDto,id);
    }

    @GetMapping("/evs/{id}")
    public Optional<EvModel> getEvById(@PathVariable String id){
        return evService.getEvById(id);
    }

    @DeleteMapping("/evs/{id}")
    public boolean deleteEvById(@RequestHeader("Authorization") String token,@PathVariable String id){
        return evService.deleteUserEv(token,id);    
    }

}
