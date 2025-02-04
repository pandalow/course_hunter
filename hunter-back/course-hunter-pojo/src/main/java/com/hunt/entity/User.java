package com.hunt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "update_time")
    private Instant updateTime;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "is_verified")
    private boolean isVerified;

    @PrePersist
    public void onCreeat() {
        createTime = Instant.now();
        updateTime = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        updateTime = Instant.now();
    }

}
