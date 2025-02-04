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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = FilterServiceImpl.class)
class FilterServiceImplTest {
    @MockBean
    private CountryDAO countryDAO;
    @MockBean
    private InstitutionDAO institutionDAO;
    @MockBean
    private ProgramDAO programDAO;

    @Autowired
    private FilterService filterService;
    private List<Country> countries;
    private List<Institution> institutions;
    private List<Program> programs;


    @BeforeEach
    void setUp() {
        countries = List.of(
                new Country(
                        1L, "Ireland", "ddd", 2, "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg"
                ),
                new Country(
                        2L, "United Kingdom", "ddd", 2, "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg"
                ));
        institutions = List.of(
                new Institution(
                        1L, "website1", "code1", 1L, "Galway"
                ),
                new Institution(
                        2L, "website2", "code2", 2L, "Limerick"
                ));

        programs = List.of(
                new Program(
                        1L, "program1", 1L, "master", "MF1", "1 year", "Science", 1L, null
                ),
                new Program(
                        2L, "program2", 2L, "master", "MF2", "2 year", "Science", 2L, null
                )
        );


    }

    @Test
    void findAllCountry() {
        when(countryDAO.findAll()).thenReturn(countries);
        List<FilterOptionItemVO> allCountry = filterService.findAllCountry();

        assertNotNull(allCountry.get(0));
        assertEquals(2, allCountry.size());
        assertEquals(1L, allCountry.get(0).getId());
        assertEquals("Ireland", allCountry.get(0).getName());
        assertEquals(2L, allCountry.get(1).getId());
        assertEquals("United Kingdom", allCountry.get(1).getName());

        verify(countryDAO, times(1)).findAll();

    }

    @Test
    void findInstitutionByPage() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<Institution> institutionPage = new PageImpl<>(institutions, pageable, institutions.size());

        when(institutionDAO.findAll(any(Pageable.class))).thenReturn(institutionPage);

        PageResult<FilterOptionItemVO> result = filterService.findInstitutionByPage(1, null);


        assertNotNull(result.getRecords().get(0));

        assertEquals(2, result.getTotal());
        assertEquals(2, result.getRecords().size());
        assertEquals(1L, result.getRecords().get(0).getId());
        assertEquals("Galway", result.getRecords().get(0).getName());
        assertEquals(2L, result.getRecords().get(1).getId());
        assertEquals("Limerick", result.getRecords().get(1).getName());

        verify(institutionDAO, times(1)).findAll(any(Pageable.class));

    }

    @Test
    void findProgramByPage() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<Program> programPage = new PageImpl<>(programs, pageable, programs.size());

        when(programDAO.findAll(any(Pageable.class))).thenReturn(programPage);

        PageResult<FilterOptionItemVO> result = filterService.findProgramByPage(1, null);

        assertNotNull(result.getRecords().get(0));

        assertEquals(2, result.getTotal());
        assertEquals(2, result.getRecords().size());
        assertEquals(1L, result.getRecords().get(0).getId());
        assertEquals("program1", result.getRecords().get(0).getName());
        assertEquals(2L, result.getRecords().get(1).getId());
        assertEquals("program2", result.getRecords().get(1).getName());

        verify(programDAO, times(1)).findAll(any(Pageable.class));
    }
}