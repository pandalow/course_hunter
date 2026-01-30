package com.hunt.dao;

import com.hunt.entity.Teacher;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherDAO extends JpaRepository<Teacher, Long> {

}
