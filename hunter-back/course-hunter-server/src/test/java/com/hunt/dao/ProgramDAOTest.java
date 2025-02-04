package com.hunt.dao;

import com.hunt.entity.CourseUGC;
import com.hunt.entity.Program;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Use H2 database
class ProgramDAOTest {

    @Autowired
    private ProgramDAO programDAO;

    @Autowired
    private TestEntityManager testEntityManager;

    private Program program1;
    private Program program2;

    @BeforeEach
    void setUp() {
        CourseUGC courseUGC1 = new CourseUGC();
        courseUGC1.setInstitutionId(1L);
        courseUGC1.setCountryId(1L);
        courseUGC1.setRating(4.5);
        courseUGC1.setCredit(3);
        courseUGC1.setLatestComment("Excellent Course");
        courseUGC1 = testEntityManager.persistAndFlush(courseUGC1);

        CourseUGC courseUGC2 = new CourseUGC();
        courseUGC2.setInstitutionId(2L);
        courseUGC2.setCountryId(2L);
        courseUGC2.setRating(3.8);
        courseUGC2.setCredit(4);
        courseUGC2.setLatestComment("Good Course");
        courseUGC2 = testEntityManager.persistAndFlush(courseUGC2);

        Set<CourseUGC> courses1 = new HashSet<>();
        courses1.add(courseUGC1);

        Set<CourseUGC> courses2 = new HashSet<>();
        courses2.add(courseUGC2);

        program1 = new Program();
        program1.setName("Computer Science");
        program1.setCollegeId(1L);
        program1.setDegree("Bachelor");
        program1.setCode("CS101");
        program1.setDuration("4 years");
        program1.setCategory("Science");
        program1.setInstitutionId(1L);
        program1.setCourseUGCSet(courses1);
        program1 = testEntityManager.persistAndFlush(program1);

        program2 = new Program();
        program2.setName("Mechanical Engineering");
        program2.setCollegeId(2L);
        program2.setDegree("Bachelor");
        program2.setCode("ME101");
        program2.setDuration("4 years");
        program2.setCategory("Engineering");
        program2.setInstitutionId(2L);
        program2.setCourseUGCSet(courses2);
        program2 = testEntityManager.persistAndFlush(program2);
    }

    @Test
    void testFindByNameContainingIgnoreCase() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<Program> result = programDAO.findByNameContainingIgnoreCase("Computer", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        Program foundProgram = result.getContent().get(0);
        assertEquals(program1.getId(), foundProgram.getId());
        assertEquals(program1.getName(), foundProgram.getName());
        assertEquals(program1.getDegree(), foundProgram.getDegree());
        assertEquals(program1.getCode(), foundProgram.getCode());
    }

    @Test
    void testFindByNameContainingIgnoreCaseWithMultipleResults() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<Program> result = programDAO.findByNameContainingIgnoreCase("Engineering", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        Program foundProgram = result.getContent().get(0);
        assertEquals(program2.getId(), foundProgram.getId());
        assertEquals(program2.getName(), foundProgram.getName());
        assertEquals(program2.getDegree(), foundProgram.getDegree());
        assertEquals(program2.getCode(), foundProgram.getCode());
    }

    @Test
    void testFindByNameContainingIgnoreCaseWithNoResults() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<Program> result = programDAO.findByNameContainingIgnoreCase("Physics", pageable);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void testPagination() {
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "id"));
        Page<Program> result = programDAO.findByNameContainingIgnoreCase("Engineering", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getSize());
    }

    @Test
    void testSorting() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "name"));
        Page<Program> result = programDAO.findByNameContainingIgnoreCase("Engineering", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        Program foundProgram = result.getContent().get(0);
        assertEquals("Mechanical Engineering", foundProgram.getName());
    }
}
