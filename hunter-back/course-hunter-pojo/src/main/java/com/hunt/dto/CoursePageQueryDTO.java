package com.hunt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoursePageQueryDTO {
    private Integer pageSize;
    private Integer page;
    private String search;
    private String sortDirection;
    private String sortBy;//rating, commentsCount, latestCommentTime
}
