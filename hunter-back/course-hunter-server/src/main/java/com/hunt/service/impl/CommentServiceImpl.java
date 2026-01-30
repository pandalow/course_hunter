package com.hunt.service.impl;

import com.hunt.constant.ExceptionMessageConstant;
import com.hunt.dao.CommentDAO;
import com.hunt.dao.CourseDAO;
import com.hunt.dao.TeacherDAO;
import com.hunt.dao.UserDAO;
import com.hunt.dto.CommentDTO;
import com.hunt.entity.Comment;
import com.hunt.entity.Course;
import com.hunt.entity.Teacher;
import com.hunt.entity.User;
import com.hunt.enumerate.TargetType;
import com.hunt.service.CommentService;
import com.hunt.utils.CopyUtil;
import com.hunt.vo.CommentVO;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;
    private final UserDAO userDAO;
    private final TeacherDAO teacherDAO;
    private final CourseDAO courseDAO;

    /**
     * Save comment entity into database
     * @param commentDTO content, targetId, targetType
     * @return saved Entity
     */
    @Override
    @Transactional
    public CommentVO save(CommentDTO commentDTO) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String googleId = authentication.getName();

        User user = userDAO.findByGoogleId(googleId)
                .orElseThrow(() -> new Exception(ExceptionMessageConstant.USER_NOT_FOUND));


        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setUser(user);

        // Get enum TargetType
        TargetType type = TargetType.fromValue(Integer.parseInt(commentDTO.getTargetType()));

        switch (type){
            case TEACHER:
                Teacher teacher = teacherDAO.findById(commentDTO.getTargetId())
                        .orElseThrow(()-> new RuntimeException(ExceptionMessageConstant.TEACHER_NOT_FOUND));
                comment.setTeacher(teacher);
                break;
            case COURSE:
                Course course =  courseDAO.findById(commentDTO.getTargetId())
                        .orElseThrow(()-> new RuntimeException(ExceptionMessageConstant.COURSE_NOT_FOUND));
                comment.setCourse(course);
                break;
            default:
                throw new Exception(ExceptionMessageConstant.INVALID_TARGET_TYPE);
        }

        commentDAO.save(comment);
        return CopyUtil.copyProperties(comment, CommentVO.class);
    }

    /**
     * Logical Delete, not physical delete
     * @param id
     */
    @Override
    @Transactional
    public void delete(Long id) {

        String googleId = SecurityContextHolder.getContext().getAuthentication().getName();

        Comment comment = commentDAO.findById(id)
                .orElseThrow(() -> new RuntimeException(ExceptionMessageConstant.COMMENT_NOT_FOUND));

        if (!comment.getUser().getGoogleId().equals(googleId)) {
            throw new RuntimeException("You are not authorized to delete this comment");
        }
        comment.setDeleted(true);
        commentDAO.save(comment);
    }
}
