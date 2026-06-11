package io.github.stefanjp.storemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Store Management System – Spring Boot 3 entry point.
 *
 * <p>Modernization of the legacy Java 11/Hibernate CLI application.
 * This class bootstraps the Spring application context, triggers
 * Flyway migrations, and starts the embedded Tomcat server.
 *
 * <p>Architecture overview: see {@code SPRING_BOOT_3_ARCHITECTURE.md}
 * Migration plan:          see {@code DOMAIN_DEPENDENCY_ANALYSIS.md}
 */
@SpringBootApplication
public class StoreManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreManagementApplication.class, args);
    }
}

