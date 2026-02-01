package com.hunt.controller;

import com.hunt.constant.MessageConstant;
import com.hunt.dto.RatingDTO;
import com.hunt.result.PageResult;
import com.hunt.result.Result;
import com.hunt.service.RatingService;
import com.hunt.vo.RatingVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rating Controller
 */
@Slf4j
@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    /**
     * Create a Review Rating
     *
     * @param ratingDTO contains targetID, targetType, score, content
     * @return RatingVO been success
     */
    @PostMapping
    public Result<RatingVO> createRating(@RequestBody RatingDTO ratingDTO) throws Exception {
        log.info(ratingDTO.toString());

        RatingVO ratingVO = ratingService.save(ratingDTO);
        return Result.success(ratingVO);
    }

    /**
     * Delete a Review Rating
     *  @param id Rating ID
     * @return Success Message
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteRating(@PathVariable Long id){
        log.info("Deleted Id: "  + id);

        ratingService.delete(id);
        return Result.success(MessageConstant.DELETE_SUCCESS);
    }

    /**
     * Update a Review Rating
     *
     * @param ratingDTO contains id, score, content
     * @return Updated RatingVO
     */
    @PutMapping
    public Result<RatingVO> updateRating(@RequestBody RatingDTO ratingDTO){
        RatingVO ratingVO = ratingService.update(ratingDTO);
        return Result.success(ratingVO);
    }

    /**
     * Get Ratings by Course ID
     *
     * @param courseId Course ID
     * @return List of RatingVO
     */
    @GetMapping("/course/{courseId}")
    public Result<List<RatingVO>> getRating(@PathVariable Long courseId){
        List<RatingVO> vos = ratingService.getRatings(courseId);
        return Result.success(vos);
    }
}
