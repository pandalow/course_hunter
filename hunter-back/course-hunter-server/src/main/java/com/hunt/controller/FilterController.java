package com.hunt.controller;

import com.hunt.result.PageResult;
import com.hunt.result.Result;
import com.hunt.service.FilterService;
import com.hunt.vo.FilterOptionItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/filter")
public class FilterController {
    @Autowired
    private FilterService filterService;

    @GetMapping("/countries")
    @Cacheable(cacheNames = "commonCache", key = "'country'")
    public Result<List<FilterOptionItemVO>> findAllCountry(
    ) {
        List<FilterOptionItemVO> countries = filterService.findAllCountry();
        return Result.success(countries);
    }

    @GetMapping("/institutions")
    public Result<PageResult> getInstitutionOptionByPage(
            @RequestParam(required = true) Integer page,
            @RequestParam(required = false) String fuzzyMatchingInputValues
    ) {
        PageResult<FilterOptionItemVO> institution = filterService.findInstitutionByPage(page, fuzzyMatchingInputValues);
        return Result.success(institution);
    }

    @GetMapping("/programs")
    public Result<PageResult> getProgramOptionByPage(
            @RequestParam(required = true) Integer page,
            @RequestParam(required = false) String fuzzyMatchingInputValues
    ) {
        PageResult<FilterOptionItemVO> program = filterService.findProgramByPage(page, fuzzyMatchingInputValues);
        return Result.success(program);
    }

}
