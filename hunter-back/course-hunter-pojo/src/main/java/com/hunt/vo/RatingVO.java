package com.hunt.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingVO {
    private Long id;
    private String content;
    private Integer score;
    private Instant createTime;
    private Long userId;
    private String userName;
    private String userAvatar;
}
