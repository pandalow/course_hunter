package com.hunt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseUGCPageQueryDTO {
    private Integer page;
    private Integer pageSize;
    private String sortBy;
    private String sortDirection;
}
