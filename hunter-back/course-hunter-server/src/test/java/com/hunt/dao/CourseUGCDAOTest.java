package com.hunt.dao;

import com.hunt.entity.CourseUGC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Use H2 database
class CourseUGCDAOTest {

    @Autowired
    private CourseUGCDAO courseUGCDAO;

    @Autowired
    private TestEntityManager testEntityManager;

    private CourseUGC courseUGC1;
    private CourseUGC courseUGC2;

    @BeforeEach
    void setUp() {
        courseUGC1 = new CourseUGC();
        courseUGC1.setInstitutionId(1L);
        courseUGC1.setCountryId(1L);
        courseUGC1.setRating(4.5);
        courseUGC1.setCredit(3);
        courseUGC1.setLatestComment("Great course!");
        courseUGC1 = testEntityManager.persistAndFlush(courseUGC1);

        courseUGC2 = new CourseUGC();
        courseUGC2.setInstitutionId(2L);
        courseUGC2.setCountryId(2L);
        courseUGC2.setRating(3.8);
        courseUGC2.setCredit(4);
        courseUGC2.setLatestComment("Good course.");
        courseUGC2 = testEntityManager.persistAndFlush(courseUGC2);
    }

    @Test
    void countByCourseId() {
        // Method to be implemented
    }

    @Test
    void findByProgramId() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<CourseUGC> result = courseUGCDAO.findByProgramId(1L, 1, 5, 3.0, 5.0, pageable);

        assertNotNull(result);
        // If no content is found, this should return 0
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void findCourseByFilters() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<CourseUGC> result = courseUGCDAO.findCourseByFilters(1L, 1L, 1, 5, 3.0, 5.0, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(courseUGC1.getId(), result.getContent().get(0).getId());

        result = courseUGCDAO.findCourseByFilters(null, null, 1, 5, 3.0, 5.0, pageable);
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        List<Long> ids = Arrays.asList(courseUGC1.getId(), courseUGC2.getId());
        assertTrue(ids.contains(result.getContent().get(0).getId()));
        assertTrue(ids.contains(result.getContent().get(1).getId()));
    }
}
