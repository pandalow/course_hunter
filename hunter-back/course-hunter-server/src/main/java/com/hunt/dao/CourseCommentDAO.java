package com.hunt.dao;

import com.hunt.entity.CourseComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseCommentDAO extends JpaRepository<CourseComment, Long> {
    Optional<CourseComment> findById(Long id);

    List<CourseComment> findAllByCourseIdAndIsDeletedFalse(Long courseId, Pageable pageable);

    List<CourseComment> findAllByCourseIdAndLevelAndIsDeletedFalse(Long courseId, long level, Pageable pageable);

    Integer countByCourseIdAndIsDeletedFalse(Long courseId);

    Integer countByCourseIdAndLevelAndIsDeletedFalse(Long courseId, int level);

    List<CourseComment> findByRootIdInAndLevelNotAndIsDeletedFalse(List<Long> rootIds, int level, Pageable pageable);
}
