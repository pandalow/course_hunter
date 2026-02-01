package com.hunt.service;

import com.hunt.dto.CoursePageQueryDTO;
import com.hunt.entity.Course;
import com.hunt.result.PageResult;
import com.hunt.vo.CourseVO;


public interface CourseService {
    CourseVO getCourseById(Long id);
    PageResult getCourses(CoursePageQueryDTO pageQueryDTO);
    PageResult getCourseByQuery(String query);
}
