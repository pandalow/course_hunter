package com.hunt.dao;

import com.hunt.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InstitutionDAO extends JpaRepository<Institution,Long> {
    /**
     * Find institutions by name containing ignore case with pagination
     *
     * @param name     institution name
     * @param pageable pagination information
     * @return page of institutions
     */
    Page<Institution> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
