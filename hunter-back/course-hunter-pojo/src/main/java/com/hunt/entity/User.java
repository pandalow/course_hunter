package com.hunt.entity;

import com.hunt.enumerate.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "google_id", unique = true, nullable = false)
    private String googleId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Instant createTime;

    @Column(name = "updated_at")
    private Instant updateTime;

    @Column(name = "avatar")
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private Role role = Role.User;

    public User(String email) {
        this.email = email;
    }

    @PrePersist
    public void onCreate() {
        createTime = Instant.now();
        updateTime = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        updateTime = Instant.now();
    }

}
