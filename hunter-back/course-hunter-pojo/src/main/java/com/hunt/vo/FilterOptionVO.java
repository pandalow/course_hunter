package com.hunt.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Filter Option Entity by Home page , using for querying the courses;
 * the Option support dynamically display when filter changing.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterOptionVO implements Serializable {
    //Country VO Item
    private List<FilterOptionItemVO> countries;
    //Institution name
    private List<FilterOptionItemVO> institutions;
    //Program name
    private List<FilterOptionItemVO> programs;
}
