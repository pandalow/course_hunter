package com.hunt.controller;

import com.hunt.dto.CourseRatingCreateDTO;
import com.hunt.dto.CourseRatingDTO;
import com.hunt.dto.CourseRatingUpdateDTO;
import com.hunt.result.Result;
import com.hunt.service.CourseRatingService;
import com.hunt.vo.CourseRatingVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course-rating")
@Slf4j
public class CourseRatingController {
  @Autowired
  private CourseRatingService courseRatingService;

  @PostMapping
  public Result<CourseRatingVO> create(@RequestBody CourseRatingCreateDTO courseRatingCreateDTO) {
    log.info("create rating: {}", courseRatingCreateDTO);
    CourseRatingVO courseRatingVO = courseRatingService.save(courseRatingCreateDTO);
    return Result.success(courseRatingVO, "Course Comment created successfully");
  }

  @GetMapping("/{id}")
  public Result<CourseRatingVO> getCourseRatingById(@PathVariable Long id) {
    CourseRatingVO courseRatingVO = courseRatingService.getById(id);
    return Result.success(courseRatingVO, "Course rating retrieved successfully");
  }

  @PutMapping("/{id}")
  public Result<CourseRatingVO> updateCourseRating(@PathVariable Long id, @RequestBody CourseRatingUpdateDTO courseRatingUpdateDTO) {
    CourseRatingVO courseRatingVO = courseRatingService.update(id, courseRatingUpdateDTO);
    return Result.success(courseRatingVO, "Course rating updated successfully");
  }
}
