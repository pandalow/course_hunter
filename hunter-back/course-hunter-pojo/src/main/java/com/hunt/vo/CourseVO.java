package com.hunt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVO implements Serializable {
    private Long id;
    private String code;
    private String title;
    private String semester;
    private Integer credits;
    private String outline;
    private String assessments;
    private String institutionName;
    private String countryName;
    private List<String> teachers;
    private String comments;
    private Double rating;
}
