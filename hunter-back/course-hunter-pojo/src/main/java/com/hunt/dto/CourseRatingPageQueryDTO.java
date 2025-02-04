package com.hunt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRatingPageQueryDTO {
    private Integer page;
    private Integer pageSize;
    private Long courseId;
    private Long userId;
    private String sortBy;
    private String sortDirection;
}
