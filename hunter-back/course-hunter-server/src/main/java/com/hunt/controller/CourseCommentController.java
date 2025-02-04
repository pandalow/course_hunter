package com.hunt.controller;

import com.hunt.dto.CourseCommentCreateDTO;
import com.hunt.result.Result;
import com.hunt.service.CourseCommentService;
import com.hunt.vo.CourseCommentVO;
import jakarta.validation.constraints.Null;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course-comment")
@Slf4j
public class CourseCommentController {
  @Autowired
  private CourseCommentService courseCommentService;

  @PostMapping
  public Result<CourseCommentVO> create(@RequestBody CourseCommentCreateDTO courseCommentCreateDTO) {
    log.info("create comment: {}", courseCommentCreateDTO);
    CourseCommentVO courseCommentVO = courseCommentService.save(courseCommentCreateDTO);
    return Result.success(courseCommentVO);
  }

  @DeleteMapping("/{id}")
  public Result<Null> deleteById(@PathVariable Long id) {
    log.info("delete comment: {}", id);
    courseCommentService.deleteById(id);
    return Result.success();
  }

  @GetMapping("/{id}")
  public Result<CourseCommentVO> getById(@PathVariable Long id) {
    log.info("get comment: {}", id);
    CourseCommentVO courseCommentVO = courseCommentService.getById(id);
    return Result.success(courseCommentVO);
  }
}
