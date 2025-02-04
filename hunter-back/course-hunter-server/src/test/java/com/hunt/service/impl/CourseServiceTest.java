package com.hunt.service.impl;

import com.hunt.constant.ExceptionMessageConstant;
import com.hunt.dao.CourseDAO;
import com.hunt.dao.CourseUGCDAO;
import com.hunt.dao.TeacherDAO;
import com.hunt.dto.CoursePageQueryDTO;
import com.hunt.entity.Course;
import com.hunt.entity.CourseUGC;
import com.hunt.exception.EntityNotFoundException;
import com.hunt.result.PageResult;
import com.hunt.vo.CourseCardVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CourseServiceImpl.class})
class CourseServiceTest {
    @MockBean
    private CourseDAO courseDAO;
    @MockBean
    private TeacherDAO teacherDAO;
    @MockBean
    private CourseUGCDAO courseUGCDAO;
    @Autowired
    private CourseServiceImpl courseService;


    private Course course;
    private CourseUGC courseUGC;
    private CoursePageQueryDTO pageQueryDTO;

    @BeforeEach
    public void setUp() {
        course = new Course();
        course.setId(1L);
        course.setTitle("Test Course");

        courseUGC = new CourseUGC();
        courseUGC.setId(1L);
        courseUGC.setRating(4.5);
        courseUGC.setLatestComment("Great course!");

        pageQueryDTO = new CoursePageQueryDTO();
        pageQueryDTO.setPage(1);
        pageQueryDTO.setPageSize(10);
        pageQueryDTO.setSortDirection("ASC");
        pageQueryDTO.setSortBy("name");
        pageQueryDTO.setProgramId(1L);
        pageQueryDTO.setMinCredits(1);
        pageQueryDTO.setMaxCredits(5);
        pageQueryDTO.setMinRating(3.0);
        pageQueryDTO.setMaxRating(5.0);
    }


    @Test
    void getCourseById() {
        when(courseDAO.findById(1L)).thenReturn(Optional.of(course));
        Course courseById = courseService.getCourseById(1L);

        assertNotNull(courseById);
        assertEquals(course.getId(), courseById.getId());
        assertEquals(course.getTitle(), courseById.getTitle());
    }

    @Test
    public void testGetCourseById_NotFound() {
        when(courseDAO.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            courseService.getCourseById(1L);
        });

        assertEquals(ExceptionMessageConstant.COURSE_NOT_FOUND, exception.getMessage());

        verify(courseDAO, times(1)).findById(1L);
    }

    @Test
    public void testGetCourses() {
        List<CourseUGC> courseUGCList = Collections.singletonList(courseUGC);

        Page<CourseUGC> courseUGCPage =
                new PageImpl<>(
                        courseUGCList,
                        PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")),
                        1
                );

        when(courseUGCDAO.findByProgramId(
                eq(pageQueryDTO.getProgramId()),
                eq(pageQueryDTO.getMinCredits()),
                eq(pageQueryDTO.getMaxCredits()),
                eq(pageQueryDTO.getMinRating()),
                eq(pageQueryDTO.getMaxRating()),
                any(PageRequest.class)
        )).thenReturn(courseUGCPage);

        when(courseDAO.findById(1L)).thenReturn(Optional.of(course));
        when(teacherDAO.getTeacherNamesByCourses(1L)).thenReturn(Arrays.asList("Teacher 1", "Teacher 2"));

        PageResult<CourseCardVO> result = courseService.getCourses(pageQueryDTO);

        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRecords().size());
        assertEquals(4.5, result.getRecords().get(0).getRating());
        assertEquals("Great course!", result.getRecords().get(0).getComments());
        assertEquals(Arrays.asList("Teacher 1", "Teacher 2"), result.getRecords().get(0).getTeachers());

        verify(courseUGCDAO, times(1)).findByProgramId(
                eq(pageQueryDTO.getProgramId()),
                eq(pageQueryDTO.getMinCredits()),
                eq(pageQueryDTO.getMaxCredits()),
                eq(pageQueryDTO.getMinRating()),
                eq(pageQueryDTO.getMaxRating()),
                any(PageRequest.class)
        );

        verify(courseDAO, times(1)).findById(1L);
        verify(teacherDAO, times(1)).getTeacherNamesByCourses(1L);
    }

    @Test
    public void testGetCourses_NoProgramId() {
        pageQueryDTO.setProgramId(null);

        List<CourseUGC> courseUGCList = Collections.singletonList(courseUGC);
        Page<CourseUGC> courseUGCPage = new PageImpl<>(courseUGCList, PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")), 1);

        when(courseUGCDAO.findCourseByFilters(
                eq(pageQueryDTO.getInstitutionId()),
                eq(pageQueryDTO.getCountryId()),
                eq(pageQueryDTO.getMinCredits()),
                eq(pageQueryDTO.getMaxCredits()),
                eq(pageQueryDTO.getMinRating()),
                eq(pageQueryDTO.getMaxRating()),
                any(PageRequest.class)
        )).thenReturn(courseUGCPage);

        when(courseDAO.findById(1L)).thenReturn(Optional.of(course));
        when(teacherDAO.getTeacherNamesByCourses(1L)).thenReturn(Arrays.asList("Teacher 1", "Teacher 2"));

        PageResult<CourseCardVO> result = courseService.getCourses(pageQueryDTO);

        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRecords().size());
        assertEquals(4.5, result.getRecords().get(0).getRating());
        assertEquals("Great course!", result.getRecords().get(0).getComments());
        assertEquals(Arrays.asList("Teacher 1", "Teacher 2"), result.getRecords().get(0).getTeachers());

        verify(courseUGCDAO, times(1)).findCourseByFilters(
                eq(pageQueryDTO.getInstitutionId()),
                eq(pageQueryDTO.getCountryId()),
                eq(pageQueryDTO.getMinCredits()),
                eq(pageQueryDTO.getMaxCredits()),
                eq(pageQueryDTO.getMinRating()),
                eq(pageQueryDTO.getMaxRating()),
                any(PageRequest.class)
        );

        verify(courseDAO, times(1)).findById(1L);
        verify(teacherDAO, times(1)).getTeacherNamesByCourses(1L);
    }
}