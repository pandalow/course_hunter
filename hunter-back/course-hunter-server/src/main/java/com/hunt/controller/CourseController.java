package com.hunt.controller;

import com.hunt.dto.CourseCommentPageQueryDTO;
import com.hunt.dto.CoursePageQueryDTO;
import com.hunt.dto.UserTokenDTO;
import com.hunt.entity.Course;
import com.hunt.result.PageResult;
import com.hunt.result.Result;
import com.hunt.service.CourseCommentService;
import com.hunt.service.CourseRatingService;
import com.hunt.service.CourseService;
import com.hunt.vo.CourseCardVO;
import com.hunt.vo.CourseCommentVO;
import com.hunt.vo.CourseRatingVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private CourseCommentService courseCommentService;
    @Autowired
    private CourseRatingService courseRatingService;

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
    @Cacheable(cacheNames = "commonCache",
            key = "'page:' + #page " +
                    "+ '-pageSize:' " +
                    "+ #pageSize + '-countryId:' " +
                    "+ #sortDirection + '-sortBy:' + #sortBy")
    public Result getCourses(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false, defaultValue = "commentsCount") String sortBy) {

        CoursePageQueryDTO pageQueryDTO = CoursePageQueryDTO.builder()
                .page(page)
                .pageSize(pageSize)
                .search(search)
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

    @GetMapping("/{id}/comments")
    public Result<PageResult<CourseCommentVO>> queryPagedTreeCommentsByCourseId(@PathVariable Long id, @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                                                @RequestParam(required = false, defaultValue = "asc") String sortDirection,
                                                                                @RequestParam(required = false, defaultValue = "createTime") String sortBy) {
        log.info("get comment list: {}", id);

        CourseCommentPageQueryDTO courseCommentPageQueryDTO = CourseCommentPageQueryDTO.builder()
                .page(page)
                .pageSize(pageSize)
                .courseId(id)
                .sortDirection(sortDirection)
                .sortBy(sortBy)
                .build();


        PageResult<CourseCommentVO> courseComments = courseCommentService.queryPagedTreeCommentsByCourseId(courseCommentPageQueryDTO);
        return Result.success(courseComments);
    }

    @GetMapping("/{id}/rating")
    public Result<CourseRatingVO> getCourseRatingByCourseId(@PathVariable Long id) {
        UserTokenDTO userDetails = (UserTokenDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        CourseRatingVO courseRatingVO = courseRatingService.getByCourseIdAndUserId(id, userId);
        return Result.success(courseRatingVO, "Course rating retrieved successfully");
    }
}
