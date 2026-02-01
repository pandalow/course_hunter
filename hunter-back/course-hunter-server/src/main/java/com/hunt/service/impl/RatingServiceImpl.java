package com.hunt.service.impl;

import com.hunt.constant.ExceptionMessageConstant;
import com.hunt.dao.CourseDAO;
import com.hunt.dao.RatingDAO;
import com.hunt.dao.UserDAO;
import com.hunt.dto.RatingDTO;
import com.hunt.entity.Course;
import com.hunt.entity.Rating;
import com.hunt.entity.User;
import com.hunt.exception.EntityNotFoundException;
import com.hunt.service.RatingService;
import com.hunt.vo.RatingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingDAO ratingDAO;
    private final UserDAO userDAO;
    private final CourseDAO courseDAO;

    /**
     * Utils Function
     *
     * @return
     */
    private User getCurrentUser() {
        String googleId = SecurityContextHolder.getContext().getAuthentication().getName();

        return userDAO.findByGoogleId(googleId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessageConstant.USER_NOT_FOUND));
    }

    private RatingVO covertToVO(Rating rating) {
        return RatingVO.builder()
                .content(rating.getContent())
                .id(rating.getId())
                .score(rating.getScore())
                .userId(rating.getUser().getId())
                .userAvatar(rating.getUser().getAvatar())
                .userName(rating.getUser().getName())
                .createTime(rating.getCreateTime())
                .build();
    }

    /**
     * Create a rating save into database
     *
     * @param ratingDTO
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public RatingVO save(RatingDTO ratingDTO) throws Exception {

        User user = getCurrentUser();

        Rating rating = new Rating();
        rating.setScore(ratingDTO.getScore());
        rating.setContent(ratingDTO.getContent());
        rating.setUser(user);

        Course course = courseDAO.findById(ratingDTO.getTargetId())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessageConstant.COURSE_NOT_FOUND));

        rating.setCourse(course);

        ratingDAO.save(rating);

        return covertToVO(rating);
    }

    /**
     * @param id
     */
    @Override
    @Transactional
    public void delete(Long id) {
        User user = getCurrentUser();

        Rating rating = ratingDAO.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessageConstant.USER_NOT_FOUND)
        );

        if (!rating.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this rating");
        }

        ratingDAO.delete(rating);
    }

    /**
     * @param ratingDTO
     * @return
     */
    @Override
    @Transactional
    public RatingVO update(RatingDTO ratingDTO) {
        User user = getCurrentUser();

        Rating rating = ratingDAO.findById(ratingDTO.getTargetId()).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessageConstant.USER_NOT_FOUND)
        );

        if (!rating.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this rating");
        }

        rating.setContent(ratingDTO.getContent());
        rating.setScore(ratingDTO.getScore());

        ratingDAO.save(rating);
        return covertToVO(rating);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingVO> getRatings(Long courseId) {
        List<Rating> ratings = ratingDAO.findByCourseId(courseId);
        return ratings.stream()
                .map(this::covertToVO)
                .toList();
    }
}
