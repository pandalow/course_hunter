package com.hunt.dao;


import com.hunt.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDAO extends JpaRepository<Course, Long> {

    /**
     * Find all courses with pagination, including related entities
     *
     * @param pageable pagination information
     * @return paginated list of courses with related entities
     */
    @Override
    @NonNull
    @EntityGraph(attributePaths = {"teachers", "institution", "country"})
    Page<Course> findAll(@NonNull Pageable pageable);

    /**
     * Find all courses by their IDs, including related entities
     *
     * @param ids list of course IDs
     * @return list of courses with related entities
     */

    @NonNull
    @EntityGraph(attributePaths = {"teachers", "institution", "country"})
    List<Course> findAllByIdIn(@NonNull List<Long> ids);
}
