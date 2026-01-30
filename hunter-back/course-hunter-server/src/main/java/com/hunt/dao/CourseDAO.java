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

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"teachers", "institution", "country"})
    Page<Course> findAll(@NonNull Pageable pageable);

    @NonNull
    @EntityGraph(attributePaths = {"teachers", "institution", "country"})
    List<Course> findAllByIdIn(@NonNull List<Long> ids);
}
