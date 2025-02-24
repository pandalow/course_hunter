package com.hunt.service;

import com.hunt.dto.CoursePageQueryDTO;
import com.hunt.entity.Course;
import com.hunt.result.PageResult;

import java.util.List;
import java.util.Map;

public interface CourseService {
    Course getCourseById(Long id);
    PageResult getCourses(CoursePageQueryDTO pageQueryDTO);

    PageResult getCourseByQuery(String query);
}
