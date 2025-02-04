package com.hunt.dao;

import com.hunt.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryDAO extends JpaRepository<Country,Long> {

    List<Country> findAll();
}
