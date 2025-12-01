package com.ev_booking_system.api.dto;

import java.util.Date;

import com.ev_booking_system.api.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String phone;
    private Role role; // USER, OWNER, ADMIN
    private Date createdAt;
}
