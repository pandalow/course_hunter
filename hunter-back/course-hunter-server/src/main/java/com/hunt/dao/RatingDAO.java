package com.hunt.dao;

import com.hunt.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingDAO extends JpaRepository<Rating, Long> {
    List<Rating> findByCourseId(Long courseId);
}
