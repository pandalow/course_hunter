package com.hunt.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRatingUpdateDTO implements Serializable {
    private Integer rating;
    private String content;
}
