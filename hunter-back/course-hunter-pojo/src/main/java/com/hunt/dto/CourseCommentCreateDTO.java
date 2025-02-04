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
public class CourseCommentCreateDTO implements Serializable {
    private String content;
    private Long courseId;
    private Long userId;
    private Integer level;
    private Long parentId;
    private Long rootId;
}
