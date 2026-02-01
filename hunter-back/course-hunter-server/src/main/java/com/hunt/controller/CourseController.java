package com.hunt.controller;

import com.hunt.dto.CoursePageQueryDTO;
import com.hunt.entity.Course;
import com.hunt.result.PageResult;
import com.hunt.result.Result;
import com.hunt.service.CourseService;
import com.hunt.vo.CourseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Course Controller
 */
@Slf4j
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    /**
     * Get course by ID, url {id} represent the resources of database
     *
     * @param id id
     * @return course content
     */
    @GetMapping("/{id}")
    public Result getCourseById(@PathVariable Long id) {
       CourseVO course = courseService.getCourseById(id);
        return Result.success(course);
    }

    /**
     * This controller can get all information or get specific course by country, institution, program id;
     * Using redis to store the Json file and restore the Json file
     *
     * @param page          not required set default 1;
     * @param pageSize      not required, set default 20;
     * @param search        not required, used to search value
     * @param sortDirection set default by asc
     * @param sortBy        set default by id (changed from commentCount which is a @Formula field and cannot be sorted)
     * @return Card information which display on the home page
     */
    @GetMapping
    public Result getCourses(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "12") Integer pageSize,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false, defaultValue = "id") String sortBy) {

        CoursePageQueryDTO pageQueryDTO = CoursePageQueryDTO.builder()
                .page(page)
                .pageSize(pageSize)
                .sortDirection(sortDirection)
                .sortBy(sortBy)
                .build();

        //If there is no match in redis, store the returned data.
        PageResult courses = courseService.getCourses(pageQueryDTO);

        return Result.success(courses);
    }

    /**
     * Search Course through S-BERT integration services;
     * @param query
     * @return List of CourseVO matching the search query;
     */
    @GetMapping("/find")
    public Result<PageResult<CourseVO>> searchCourse(@RequestParam("query") String query) {
        PageResult<CourseVO> courses = courseService.getCourseByQuery(query);
        return Result.success(courses);
    }
}
