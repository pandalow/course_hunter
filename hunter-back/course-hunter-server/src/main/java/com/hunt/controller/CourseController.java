package com.hunt.controller;

import com.hunt.dto.CoursePageQueryDTO;
import com.hunt.entity.Course;
import com.hunt.result.PageResult;
import com.hunt.result.Result;
import com.hunt.service.CourseService;
import com.hunt.vo.CourseCardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST API for course
 */
@Slf4j
@RestController
@RequestMapping("/course")

public class CourseController {
    @Autowired
    private CourseService courseService;

    /**
     * Get course by ID, url {id} represent the resources of database
     *
     * @param id
     * @return course content
     */
    @GetMapping("/{id}")
    public Result getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
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
     * @param sortBy        set default by comments_count
     * @return Card information which display on the home page
     */
    @GetMapping
//    @Cacheable(cacheNames = "commonCache",
//            key = "'page:' + #page " +
//                    "+ '-pageSize:' " +
//                    "+ #pageSize + '-countryId:' " +
//                    "+ #sortDirection + '-sortBy:' + #sortBy")
    public Result getCourses(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "12") Integer pageSize,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false, defaultValue = "commentsCount") String sortBy) {

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

    @GetMapping("/find")
    public Result<PageResult<CourseCardVO>> searchCourse(@RequestParam("query") String query) {
        PageResult<CourseCardVO> courses = courseService.getCourseByQuery(query);
        return Result.success(courses);
    }
}
