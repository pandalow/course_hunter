package com.hunt.dao;

import com.hunt.dto.CoursePageQueryDTO;
import com.hunt.entity.Course;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDAO extends JpaRepository<Course, Long> {
    Course findCourseById(Long id);

    @Query("select c.title from Course c "   )
    List<String> findAllCourseTitle();
}
