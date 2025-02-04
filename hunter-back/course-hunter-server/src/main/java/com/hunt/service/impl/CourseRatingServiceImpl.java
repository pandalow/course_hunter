package com.hunt.service.impl;

import com.hunt.constant.ExceptionMessageConstant;
import com.hunt.dao.CourseRatingDAO;
import com.hunt.dao.CourseUGCDAO;
import com.hunt.dto.CourseRatingCreateDTO;
import com.hunt.dto.CourseRatingPageQueryDTO;
import com.hunt.dto.CourseRatingUpdateDTO;
import com.hunt.entity.CourseRating;
import com.hunt.entity.CourseUGC;
import com.hunt.result.PageResult;
import com.hunt.service.CourseRatingService;
import com.hunt.utils.CopyUtil;
import com.hunt.vo.CourseRatingVO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class CourseRatingServiceImpl implements CourseRatingService {
    private CourseRatingDAO courseRatingDAO;
    private CourseUGCDAO courseUGCDAO;

    @Override
    public CourseRatingVO save(CourseRatingCreateDTO courseRatingCreateDTO) {
        log.info("save course rating: {}", courseRatingCreateDTO);

        CourseRating courseRating = CopyUtil.copyProperties(courseRatingCreateDTO, CourseRating.class);
        courseRatingDAO.save(courseRating);

        Long courseId = courseRatingCreateDTO.getCourseId();
        updateCourseUGCIfNeeded(courseId);

        return CopyUtil.copyProperties(courseRating, CourseRatingVO.class);
    }

    private void updateCourseUGCIfNeeded(Long courseId) {
        long count = courseRatingDAO.countByCourseId(courseId);
        if (count <= 100) {
            updateCourseImmediately(courseId);
        } else {
            // 100 comments, update at midnight
            scheduleCourseUpdate(courseId);
        }
    }

    private void updateCourseImmediately(Long courseId) {
        long rating = courseRatingDAO.findAverageRatingByCourseId(courseId);

        CourseUGC courseUGC = courseUGCDAO.findById(courseId).orElseGet(() -> {
            CourseUGC newCourseUGC = new CourseUGC();
            newCourseUGC.setId(courseId);
            return newCourseUGC;
        });

        courseUGC.setRating(rating);

        courseUGCDAO.save(courseUGC);
    }

    // FIXME: @Scheduled methods must not take any arguments or return anything - in the 3rd pr
    @Scheduled(cron = "0 0 0 * * ?") // execute at every midnight
    private void scheduleCourseUpdate(Long courseId) {
        updateCourseImmediately(courseId);
    }

    @Override
    public CourseRatingVO getById(Long id) {
        log.info("get rating: {}", id);
        Optional<CourseRating> courseRating = courseRatingDAO.findById(id);

        return CopyUtil.copyProperties(courseRating, CourseRatingVO.class);
    }

    @Override
    public CourseRatingVO update(Long id, CourseRatingUpdateDTO courseRatingUpdateDTO) {
        log.info("update course rating: {}", courseRatingUpdateDTO);

        CourseRating courseRating = courseRatingDAO.findById(id).orElseThrow(() -> new EntityNotFoundException(ExceptionMessageConstant.COURSE_RATING_NOT_FOUND));
        courseRating.setRating(courseRatingUpdateDTO.getRating());
        courseRating.setContent(courseRatingUpdateDTO.getContent());
        courseRatingDAO.save(courseRating);

        Long courseId = courseRating.getCourseId();
        updateCourseUGCIfNeeded(courseId);

        return CopyUtil.copyProperties(courseRating, CourseRatingVO.class);
    }

    @Override
    public CourseRatingVO getByCourseIdAndUserId(Long courseId, Long userId) {
        log.info("get course rating: {}, {}", courseId, userId);
        CourseRating courseRating = courseRatingDAO.findByCourseIdAndUserId(courseId, userId);

        return CopyUtil.copyProperties(courseRating, CourseRatingVO.class);
    }

    @Override
    public PageResult<CourseRatingVO> getByUserId(Long userId, CourseRatingPageQueryDTO queryDTO) {
        log.info("get user rating: {}", userId);
        // build pageable
        Sort sort = Sort.by(Sort.Direction.fromString(queryDTO.getSortDirection()), queryDTO.getSortBy());
        Pageable pageable = PageRequest.of(queryDTO.getPage() - 1, queryDTO.getPageSize(), sort);

        List<CourseRating> courseRatings = courseRatingDAO.findAllByUserId(userId, pageable);

        Long total = courseRatingDAO.countByUserId(userId);

        List<CourseRatingVO> courseRatingVOs = courseRatings.stream()
                .map(courseRating -> CopyUtil.copyProperties(courseRating, CourseRatingVO.class)).toList();

        return new PageResult<>(total, courseRatingVOs);
    }
}