package com.hunt.dao;

import com.hunt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByGoogleId(String googleId);


}
