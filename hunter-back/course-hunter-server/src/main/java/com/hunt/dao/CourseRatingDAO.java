package com.hunt.dao;

import com.hunt.entity.CourseRating;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRatingDAO extends JpaRepository<CourseRating, Long> {

    Optional<CourseRating> findById(Long id);

    List<CourseRating> findAllByCourseId(Long courseId, Pageable pageable);

    @Query("SELECT AVG(s.rating) FROM CourseRating s WHERE s.courseId = :courseId")
    Long findAverageRatingByCourseId(@Param("courseId") Long courseId);

    Long countByCourseId(Long courseId);

    Long countByUserId(Long userId);

    @Transactional
    void deleteById(Long id);

    CourseRating findByCourseIdAndUserId(Long courseId, Long userId);

    List<CourseRating> findAllByUserId(Long userId, Pageable pageable);
}
