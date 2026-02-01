package com.hunt.service;

import com.hunt.dto.RatingDTO;
import com.hunt.vo.RatingVO;

import java.util.List;

public interface RatingService {
    RatingVO save(RatingDTO ratingDTO) throws Exception;
    void delete(Long id);
    RatingVO update(RatingDTO ratingDTO);
    List<RatingVO> getRatings(Long courseId);
}
