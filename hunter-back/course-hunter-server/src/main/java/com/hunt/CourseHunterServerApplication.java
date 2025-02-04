package com.hunt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableCaching // Using Spring Cache to manage Redis
@EnableTransactionManagement // Transactional management

public class CourseHunterServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseHunterServerApplication.class, args);
    }

}
