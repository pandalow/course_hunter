package com.hunt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rate")
@RequiredArgsConstructor
public class RatingController {
    /**
     *
     */
    @PostMapping
    public Result<RatingVO> createRating(@RequestBody RatingDTO ratingDTO){

    }
}
