package com.hunt.controller;

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

@Slf4j
@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    /**
     * Create a Review Rating
     *
     * @param ratingDTO cotains targetID, targetType, score, content
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
     *
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteRating(@PathVariable Long id){
        log.info("Deleted Id"  + id);

        ratingService.delete(id);
        return Result.success("Delete Success");
    }

    @PutMapping
    public Result<RatingVO> updateRating(@RequestBody RatingDTO ratingDTO){
        RatingVO ratingVO = ratingService.update(ratingDTO);
        return Result.success(ratingVO);
    }

    @GetMapping("/course/{courseId}")
    public Result<List<RatingVO>> getRating(@PathVariable Long course_id){
        List<RatingVO> vos = ratingService.getRatings(course_id);
        return Result.success(vos);
    }
}
