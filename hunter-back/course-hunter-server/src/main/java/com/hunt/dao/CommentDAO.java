package com.hunt.dao;

import com.hunt.entity.Comment;
import com.hunt.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDAO extends JpaRepository<Comment, Long> {


}
