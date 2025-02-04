package com.hunt.dao;

import com.hunt.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InstitutionDAO extends JpaRepository<Institution,Long> {
    Page<Institution> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
