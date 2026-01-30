package com.hunt.service;

import com.hunt.dto.CommentDTO;
import com.hunt.vo.CommentVO;

public interface CommentService {
    CommentVO save(CommentDTO commentDTO) throws Exception;

    void delete(Long id);
}
