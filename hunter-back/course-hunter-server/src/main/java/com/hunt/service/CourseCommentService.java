package com.hunt.service;

import com.hunt.dto.CourseCommentCreateDTO;
import com.hunt.dto.CourseCommentPageQueryDTO;
import com.hunt.result.PageResult;
import com.hunt.vo.CourseCommentVO;

import java.util.List;

public interface CourseCommentService {
    CourseCommentVO save(CourseCommentCreateDTO courseCommentCreateDTO);
    void deleteById(Long commentId);
    CourseCommentVO getById(Long commentId);
    PageResult<CourseCommentVO> queryPagedTreeCommentsByCourseId(CourseCommentPageQueryDTO courseCommentPageQueryDTO);
}
