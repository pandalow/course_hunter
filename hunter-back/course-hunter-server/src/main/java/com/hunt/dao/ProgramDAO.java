package com.hunt.dao;

import com.hunt.entity.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramDAO extends JpaRepository<Program,Long> {
    Page<Program> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
