package com.hunt.dao;

import com.hunt.entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Use H2 database
class CountryDAOTest {

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private TestEntityManager testEntityManager;

    private Country country1;
    private Country country2;

    @BeforeEach
    void setUp() {
        country1 = new Country();
        country1.setName("United States");
        country1.setCountryCode("US");
        country1.setCode(1);
        country1.setChineseName("美国");
        country1.setFrenchName("États-Unis");
        country1.setItalianName("Stati Uniti");
        country1.setJapaneseName("アメリカ");
        country1.setRussianName("Соединенные Штаты");
        country1.setGermanName("Vereinigte Staaten");
        country1.setSpanishName("Estados Unidos");
        country1 = testEntityManager.persistAndFlush(country1);

        country2 = new Country();
        country2.setName("Canada");
        country2.setCountryCode("CA");
        country2.setCode(2);
        country2.setChineseName("加拿大");
        country2.setFrenchName("Canada");
        country2.setItalianName("Canada");
        country2.setJapaneseName("カナダ");
        country2.setRussianName("Канада");
        country2.setGermanName("Kanada");
        country2.setSpanishName("Canadá");
        country2 = testEntityManager.persistAndFlush(country2);
    }

    @Test
    void testFindAll() {
        List<Country> countries = countryDAO.findAll();
        assertNotNull(countries);
        assertEquals(2, countries.size());
        assertTrue(countries.stream().anyMatch(country -> country.getName().equals("United States")));
        assertTrue(countries.stream().anyMatch(country -> country.getName().equals("Canada")));
    }
}
