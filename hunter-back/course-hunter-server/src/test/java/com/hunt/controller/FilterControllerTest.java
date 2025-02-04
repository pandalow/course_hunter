package com.hunt.controller;

import com.fasterxml.jackson.databind.introspect.AnnotatedAndMetadata;
import com.hunt.result.PageResult;
import com.hunt.service.FilterService;
import com.hunt.vo.FilterOptionItemVO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(FilterController.class)
@AutoConfigureMockMvc
class FilterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilterService filterService;

    @Test
    void findAllCountry() throws Exception {
        Mockito
                .when(filterService.findAllCountry())
                .thenReturn(List.of(
                        new FilterOptionItemVO(1L, "Ireland"),
                        new FilterOptionItemVO(2L, "United Kingdom")
                ));
        mockMvc.perform(MockMvcRequestBuilders.get("/filter/countries"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value("Ireland"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].name").value("United Kingdom"));

    }

    @Test
    void getInstitutionOptionByPage() throws Exception {
        Mockito
                .when(filterService.findInstitutionByPage(1, null))
                .thenReturn(new PageResult<>(1, List.of(
                        new FilterOptionItemVO(1L, "University of Galway"),
                        new FilterOptionItemVO(2L, "University of Limerick")
                )));
        mockMvc.perform(MockMvcRequestBuilders.get("/filter/institutions?page=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.records[0].id").value(1))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data.records[0].name").value("University of Galway")
                );
    }

    @Test
    void getProgramOptionByPage() throws Exception {
        Mockito
                .when(filterService.findProgramByPage(1, null))
                .thenReturn(new PageResult<>(1, List.of(
                        new FilterOptionItemVO(1L, "Computer Science"),
                        new FilterOptionItemVO(2L, "Data Science")
                )));
        mockMvc.perform(MockMvcRequestBuilders.get("/filter/programs?page=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.records[0].id").value(1))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data.records[0].name").value("Computer Science")
                );
    }
}