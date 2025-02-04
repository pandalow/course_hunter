package com.hunt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCommentPageQueryDTO {
    private Integer page;
    private Integer pageSize;
    private Long courseId;
    private String sortBy;
    private String sortDirection;
}
