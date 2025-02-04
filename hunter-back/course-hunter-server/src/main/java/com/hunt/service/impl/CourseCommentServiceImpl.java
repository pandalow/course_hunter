package com.hunt.service.impl;

import com.hunt.constant.ExceptionMessageConstant;
import com.hunt.dao.CourseCommentDAO;
import com.hunt.dao.CourseUGCDAO;
import com.hunt.dto.CourseCommentCreateDTO;
import com.hunt.dto.CourseCommentPageQueryDTO;
import com.hunt.entity.CourseComment;
import com.hunt.entity.CourseUGC;
import com.hunt.entity.User;
import com.hunt.result.PageResult;
import com.hunt.service.CourseCommentService;
import com.hunt.utils.CopyUtil;
import com.hunt.vo.CourseCommentVO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CourseCommentServiceImpl implements CourseCommentService {
    private CourseCommentDAO courseCommentDAO;
    private CourseUGCDAO courseUGCDAO;

    @Override
    public CourseCommentVO save(CourseCommentCreateDTO courseCommentCreateDTO) {
        log.info("save course comment: {}", courseCommentCreateDTO);

        CourseComment courseComment = CopyUtil.copyProperties(courseCommentCreateDTO, CourseComment.class);
        courseCommentDAO.save(courseComment);

        Long courseId = courseCommentCreateDTO.getCourseId();
        updateCourseUGCIfNeeded(courseId);

        return CopyUtil.copyProperties(courseComment, CourseCommentVO.class);
    }

    /**
     * Update course info if needed.
     * The idea is that there are less than 100 user comments,
     * and each score may have a large impact on the average score,
     * so it is updated in real time, but then the cache will be updated first.
     *
     * @param courseId - the course id
     */
    private void updateCourseUGCIfNeeded(Long courseId) {
        long levelZeroCount = courseCommentDAO.countByCourseIdAndLevelAndIsDeletedFalse(courseId, 0);
        if (levelZeroCount <= 100) {
            updateCourseUGCImmediately(courseId);
        } else {
            // 100 comments, update at midnight
            scheduleCourseUGCUpdate(courseId);
        }
    }

    private void updateCourseUGCImmediately(Long courseId) {
        List<CourseComment> comments = courseCommentDAO.findAllByCourseIdAndLevelAndIsDeletedFalse(courseId, 0, Pageable.unpaged());

        String latestComment = comments.stream()
                .sorted(Comparator.comparing(CourseComment::getCreateTime).reversed())
                .map(CourseComment::getContent)
                .findFirst()
                .orElse("");

        Instant latestCommentTime = comments.stream()
                .sorted(Comparator.comparing(CourseComment::getCreateTime).reversed())
                .map(CourseComment::getCreateTime)
                .findFirst()
                .orElse(Instant.now());

        int commentsCount = comments.size();

        CourseUGC courseUGC = courseUGCDAO.findById(courseId)
                .orElseGet(() -> {
                    CourseUGC newCourseUGC = new CourseUGC();
                    newCourseUGC.setId(courseId);
                    return newCourseUGC;
                });

        courseUGC.setLatestComment(latestComment);
        courseUGC.setLatestCommentTime(latestCommentTime);
        courseUGC.setCommentsCount(commentsCount);

        courseUGCDAO.save(courseUGC);
    }

    // FIXME: @Scheduled methods must not take any arguments or return anything - in the 3rd pr
    @Scheduled(cron = "0 0 0 * * ?") // execute at every midnight
    private void scheduleCourseUGCUpdate(Long courseId) {
        updateCourseUGCImmediately(courseId);
    }

    @Override
    public void deleteById(Long commentId) {
        log.info("delete course comment: {}", commentId);
        CourseComment courseComment = courseCommentDAO.findById(commentId).orElseThrow(() -> new EntityNotFoundException(ExceptionMessageConstant.COURSE_COMMENT_NOT_FOUND));
        courseComment.setDeleted(true);
        courseCommentDAO.save(courseComment);
    }

    @Override
    public CourseCommentVO getById(Long commentId) {
        log.info("get comment: {}", commentId);
        Optional<CourseComment> courseComment = courseCommentDAO.findById(commentId);

        return CopyUtil.copyProperties(courseComment, CourseCommentVO.class);
    }

    @Transactional
    @Override
    public PageResult<CourseCommentVO> queryPagedTreeCommentsByCourseId(CourseCommentPageQueryDTO queryDTO) {
        log.info("get comment list: {}", queryDTO);
        Long courseId = queryDTO.getCourseId();

        // build pageable
        Sort sort = Sort.by(Sort.Direction.fromString(queryDTO.getSortDirection()), queryDTO.getSortBy());
        Pageable pageable = PageRequest.of(queryDTO.getPage() - 1, queryDTO.getPageSize(), sort);

        // get root comments first
        List<CourseComment> comments = courseCommentDAO.findAllByCourseIdAndLevelAndIsDeletedFalse(courseId, 0, pageable);
        int courseCommentCount = comments.size();

        // get replies of root comments
        List<Long> commentIds = comments.stream().map(CourseComment::getId).toList();
        List<CourseComment> replies = courseCommentDAO.findByRootIdInAndLevelNotAndIsDeletedFalse(commentIds, 0, Pageable.unpaged());

        // group replies by root id
        Map<Long, List<CourseComment>> repliesMap = replies.stream()
                .collect(Collectors.groupingBy(CourseComment::getRootId));

        // build result
        List<CourseCommentVO> result = new ArrayList<>();
        comments.forEach(comment -> {
            CourseCommentVO vo = CopyUtil.copyProperties(comment, CourseCommentVO.class);
            // set replies of root comment
            User user = comment.getUser();
            if (user != null) {
                vo.setUserName(user.getUsername());
                vo.setUserAvatar(user.getAvatar());
            }

            vo.setChild(repliesMap.getOrDefault(comment.getId(), Collections.emptyList()).stream().map(reply -> {
                // convert reply to vo
                return CopyUtil.copyProperties(reply, CourseCommentVO.class);
            }).toList());

            result.add(vo);
        });

        return new PageResult<>(courseCommentCount, result);
    }
}