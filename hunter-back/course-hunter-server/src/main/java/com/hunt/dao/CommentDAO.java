package com.hunt.dao;

import com.hunt.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentDAO extends JpaRepository<Comment, Long> {

    /** 
     * Find Comments by Course ID where isDeleted is false, ordered by createTime descending
     * 
     * @param courseId the ID of the course
     * @return list of comments
     */
    List<Comment> findByCourse_IdAndIsDeletedFalseOrderByCreateTimeDesc(Long courseId);

    /** 
     * Find Comments by Teacher ID where isDeleted is false, ordered by createTime descending
     * 
     * @param teacherId the ID of the teacher
     * @return list of comments
     */
    List<Comment> findByTeacher_IdAndIsDeletedFalseOrderByCreateTimeDesc(Long teacherId);
}
