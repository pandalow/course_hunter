package com.hunt.dao;

import com.hunt.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentDAO extends JpaRepository<Comment, Long> {

    List<Comment> findByCourse_IdAndIsDeletedFalseOrderByCreateTimeDesc(Long courseId);
    List<Comment> findByTeacher_IdAndIsDeletedFalseOrderByCreateTimeDesc(Long teacherId);
}
