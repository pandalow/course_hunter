package com.hunt.dao;

import com.hunt.entity.CourseUGC;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseUGCDAO extends JpaRepository<CourseUGC, Long>, JpaSpecificationExecutor<CourseUGC> {
    Integer countByCourseId(Long courseId);
    Page<CourseUGC> findAll(Pageable pageable);

}
