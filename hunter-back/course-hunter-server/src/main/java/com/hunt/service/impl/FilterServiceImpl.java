package com.hunt.service.impl;

import com.hunt.dao.CountryDAO;
import com.hunt.dao.InstitutionDAO;
import com.hunt.dao.ProgramDAO;
import com.hunt.entity.Country;
import com.hunt.entity.Institution;
import com.hunt.entity.Program;
import com.hunt.result.PageResult;
import com.hunt.service.FilterService;
import com.hunt.vo.FilterOptionItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterServiceImpl implements FilterService {

    @Autowired
    private CountryDAO countryDAO;
    @Autowired
    private InstitutionDAO institutionDAO;
    @Autowired
    private ProgramDAO programDAO;


    @Override
    public List<FilterOptionItemVO> findAllCountry() {
        List<Country> countries = countryDAO.findAll();

        List<FilterOptionItemVO> options = countries.stream().map(country ->
                        FilterOptionItemVO
                                .builder()
                                .name(country.getName())
                                .id(country.getId())
                                .build())
                .collect(Collectors.toList());
        return options;
    }

    @Override
    public PageResult<FilterOptionItemVO> findInstitutionByPage(Integer page, String fuzzyMatchingInputValues) {
        Sort sort = Sort.by(
                Sort.Direction.fromString("asc"), "id");
        Pageable pageable = PageRequest.of(
                page - 1, 10, sort);

        Page<Institution> institutions;
        if (fuzzyMatchingInputValues == null) {
            institutions = institutionDAO.findAll(pageable);
        } else {
            institutions = institutionDAO.findByNameContainingIgnoreCase(fuzzyMatchingInputValues, pageable);
        }

        List<FilterOptionItemVO> options = institutions.stream().map(institution ->
                        FilterOptionItemVO
                                .builder()
                                .name(institution.getName())
                                .id(institution.getId())
                                .build())
                .collect(Collectors.toList());
        return new PageResult<FilterOptionItemVO>(institutions.getNumberOfElements(), options);
    }

    @Override
    public PageResult<FilterOptionItemVO> findProgramByPage(Integer page, String fuzzyMatchingInputValues) {

        Sort sort = Sort.by(
                Sort.Direction.fromString("asc"), "id");
        Pageable pageable = PageRequest.of(
                page - 1, 10, sort);

        Page<Program> programs;
        if (fuzzyMatchingInputValues == null) {
            programs = programDAO.findAll(pageable);
        } else {
            programs = programDAO.findByNameContainingIgnoreCase(fuzzyMatchingInputValues, pageable);
        }

        List<FilterOptionItemVO> options = programs.stream().map(program ->
                        FilterOptionItemVO
                                .builder()
                                .name(program.getName())
                                .id(program.getId())
                                .build())
                .collect(Collectors.toList());
        return new PageResult<FilterOptionItemVO>(programs.getNumberOfElements(), options);
    }
}