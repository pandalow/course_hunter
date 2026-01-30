package com.hunt.service;

import com.hunt.dto.CommentDTO;
import com.hunt.vo.CommentVO;

import java.util.List;

public interface CommentService {
    CommentVO save(CommentDTO commentDTO) throws Exception;

    void delete(Long id);

    List<CommentVO> getComments(Long targetId, String targetType);
}
