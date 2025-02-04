package com.hunt.service;

import com.hunt.dto.FilterOptionDTO;
import com.hunt.result.PageResult;
import com.hunt.vo.FilterOptionItemVO;
import com.hunt.vo.FilterOptionVO;

import java.util.List;

public interface FilterService {
    List<FilterOptionItemVO> findAllCountry();
    PageResult<FilterOptionItemVO> findInstitutionByPage(Integer page, String fuzzyMatchingInputValues);
    PageResult<FilterOptionItemVO> findProgramByPage(Integer page, String fuzzyMatchingInputValues);
}
