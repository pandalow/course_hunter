package com.hunt.dao;

import com.hunt.entity.Institution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Use H2 database
class InstitutionDAOTest {

    @Autowired
    private InstitutionDAO institutionDAO;

    @Autowired
    private TestEntityManager testEntityManager;

    private Institution institution1;
    private Institution institution2;

    @BeforeEach
    void setUp() {
        institution1 = new Institution();
        institution1.setWebsite("https://www.example1.com");
        institution1.setCountryCode("US");
        institution1.setCountryId(1L);
        institution1.setName("Example Institution One");
        institution1 = testEntityManager.persistAndFlush(institution1);

        institution2 = new Institution();
        institution2.setWebsite("https://www.example2.com");
        institution2.setCountryCode("CA");
        institution2.setCountryId(2L);
        institution2.setName("Example Institution Two");
        institution2 = testEntityManager.persistAndFlush(institution2);
    }

    @Test
    void testFindByNameContainingIgnoreCase() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Institution> result = institutionDAO.findByNameContainingIgnoreCase("Example", pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().stream().anyMatch(institution -> institution.getName().equals("Example Institution One")));
        assertTrue(result.getContent().stream().anyMatch(institution -> institution.getName().equals("Example Institution Two")));
    }

    @Test
    void testFindByNameContainingIgnoreCaseWithSingleResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Institution> result = institutionDAO.findByNameContainingIgnoreCase("One", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Example Institution One", result.getContent().get(0).getName());
    }

    @Test
    void testFindByNameContainingIgnoreCaseWithNoResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Institution> result = institutionDAO.findByNameContainingIgnoreCase("Nonexistent", pageable);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }
}
