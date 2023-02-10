package com.desafio.tools.management.resources.entities;

import com.desafio.tools.management.application.web.entities.requests.UserRequest;
import javax.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;

    public UserEntity(UserRequest user) {
        this.username = user.getUsername();
        this.password = new BCryptPasswordEncoder().encode(user.getPassword());
    }

    public UserEntity() {
    }

    public UserEntity(long l, String username, String pass) {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
