package com.hunt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRatingCreateDTO implements Serializable {
    private Long courseId;
    private Long userId;
    private Integer rating;
    private String content;
}
