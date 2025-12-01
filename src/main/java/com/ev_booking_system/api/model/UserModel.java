package com.ev_booking_system.api.model;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserModel {

    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private Role role; // USER, OWNER, ADMIN
    private Date createdAt;

    public void setRole(String user) {
        this.role = role;
    }

}
