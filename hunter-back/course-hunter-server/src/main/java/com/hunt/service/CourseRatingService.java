package com.hunt.service;

import com.hunt.dto.CourseRatingCreateDTO;
import com.hunt.dto.CourseRatingPageQueryDTO;
import com.hunt.dto.CourseRatingUpdateDTO;
import com.hunt.result.PageResult;
import com.hunt.vo.CourseRatingVO;

public interface CourseRatingService {
    CourseRatingVO save(CourseRatingCreateDTO courseRatingCreateDTO);
    CourseRatingVO getById(Long ratingId);
    CourseRatingVO update(Long ratingId, CourseRatingUpdateDTO courseRatingUpdateDTO);

    CourseRatingVO getByCourseIdAndUserId(Long courseId, Long userId);
    PageResult<CourseRatingVO> getByUserId(Long userId, CourseRatingPageQueryDTO courseRatingQueryDTO);
}
