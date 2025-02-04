package com.hunt.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCommentVO implements Serializable {
    private Long id;
    private String content;
    private Long courseId;
    private Long userId;
    private String userName;
    private String userAvatar;
    private Integer level;
    private Long parentId;
    private Long rootId;
    private boolean isDeleted;
    private Instant createTime;
    private List<CourseCommentVO> child;
}
