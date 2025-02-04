package com.hunt.controller;

import com.hunt.result.PageResult;
import com.hunt.service.CourseCommentService;
import com.hunt.service.CourseRatingService;
import com.hunt.service.CourseService;
import com.hunt.vo.CourseCardVO;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc()
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService courseService;
    @MockBean
    private CourseCommentService courseCommentService;
    @MockBean
    private CourseRatingService courseRatingService;

    @Test
    void testGetCourses() throws Exception {

        Mockito
                .when(courseService.getCourses(Mockito.any()))
                .thenReturn(new PageResult(
                        2,
                        List.of(
                                new CourseCardVO(
                                        1L,
                                        "AA106",
                                        "title1",
                                        "semester1",
                                        5,
                                        "outline description",
                                        "content description",
                                        "university of galway",
                                        "ireland",
                                        List.of(
                                                "teacher1", "teacher2"
                                        ),
                                        "comments",
                                        2.1

                                ),
                                new CourseCardVO(
                                        2L,
                                        "AA107",
                                        "title2",
                                        "semester2",
                                        6,
                                        "outline description",
                                        "content description",
                                        "university of galway",
                                        "ireland",
                                        List.of(
                                                "teacher1", "teacher2"
                                        ),
                                        "comments",
                                        2.1
                                )
                        )));
        mockMvc.perform(MockMvcRequestBuilders.get("/course?page=1&pageSize=20&minRating=0&maxRating=5&minCredit=0&maxCredit=100&sortDirection=asc&sortBy=commentsCount"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.records[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.records[0].code").value("AA106"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.records[0].title").value("title1"));
    }
}