package com.hunt.service;

import com.hunt.service.impl.CourseServiceImpl;
import com.hunt.service.impl.FastApiClientServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hunt.dao.CourseDAO;
import com.hunt.dto.CoursePageQueryDTO;
import com.hunt.dto.FastApiResponse;
import com.hunt.entity.Country;
import com.hunt.entity.Course;
import com.hunt.entity.Institution;
import com.hunt.result.PageResult;
import com.hunt.vo.CourseVO;
import com.hunt.vo.ResultItem;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for CourseServiceImpl.
 * We use MockitoExtension for fast, standalone testing without loading Spring Context.
 */

@ExtendWith(MockitoExtension.class)
class CourseServiceImplUnitTest {

    @Mock
    private CourseDAO courseDAO;

    @Mock
    private FastApiClientServiceImpl fastApiClientServiceImpl;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course mockCourse;
    private Institution mockInstitution;
    private Country mockCountry;

    @BeforeEach
    void setUp() {
        // Initialize Metadata
        mockInstitution = new Institution();
        mockInstitution.setName("University College Dublin");

        mockCountry = new Country();
        mockCountry.setName("Ireland");

        // Initialize Mock Course
        mockCourse = new Course();
        mockCourse.setId(101L);
        mockCourse.setTitle("Advanced Java");
        mockCourse.setInstitution(mockInstitution);
        mockCountry.setName("Ireland"); // Fixing setCountry call logic
        mockCourse.setCountry(mockCountry);
        mockCourse.setTeachers(new HashSet<>());

        // Manual assignment of formula-based values for VO mapping check
        // Note: In real DB these are @Formula, in Mock we just ensure the getters exist
    }

    @Test
    @DisplayName("Should return paginated CourseVOs successfully")
    void getCourses_Success() {
        // Arrange
        CoursePageQueryDTO dto = CoursePageQueryDTO.builder()
                .page(1)
                .pageSize(10)
                .sortBy("title")
                .sortDirection("ASC")
                .build();

        Page<Course> coursePage = new PageImpl<>(Collections.singletonList(mockCourse));
        when(courseDAO.findAll(any(Pageable.class))).thenReturn(coursePage);

        // Act
        PageResult<CourseVO> result = courseService.getCourses(dto);

        // Assert
        assertNotNull(result);
        assertFalse(result.getRecords().isEmpty());
        CourseVO vo = result.getRecords().get(0);
        assertEquals("Advanced Java", vo.getTitle());
        assertEquals("University College Dublin", vo.getInstitutionName());
        verify(courseDAO).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should return CourseVOs based on FastAPI search results")
    void getCourseByQuery_Success() {
        // Arrange
        String searchQuery = "Java";
        ResultItem item = new ResultItem();
        item.setId(101L);

        FastApiResponse apiResponse = new FastApiResponse();
        apiResponse.setResults(Collections.singletonList(item));

        // Mocking the reactive call to return a valid Mono instead of null
        when(fastApiClientServiceImpl.getFastApiResult(anyString())).thenReturn(Mono.just(apiResponse));

        // Mocking the batch DB query
        when(courseDAO.findAllByIdIn(anyList())).thenReturn(Collections.singletonList(mockCourse));

        // Act
        PageResult<CourseVO> result = courseService.getCourseByQuery(searchQuery);

        // Assert
        assertNotNull(result, "PageResult should not be null");
        assertEquals(1, result.getTotal());
        CourseVO vo = result.getRecords().get(0);
        assertEquals(101L, vo.getId());
        assertEquals("Ireland", vo.getCountryName());

        verify(fastApiClientServiceImpl).getFastApiResult(searchQuery);
        verify(courseDAO).findAllByIdIn(anyList());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when course ID is invalid")
    void getCourseById_NotFound() {
        // Arrange
        when(courseDAO.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> courseService.getCourseById(999L));
    }
}