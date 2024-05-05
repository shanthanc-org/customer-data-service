package org.shanthan.customerdataservice;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class BaseIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password")
            .withInitScript("init.sql");

    @DynamicPropertySource
    static void prepareDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
    }

    @BeforeAll
    static void setUp() {
        postgreSQLContainer.start();

        // Apply migrations explicitly if not automatically applied
        Flyway.configure()
                .dataSource(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(),
                        postgreSQLContainer.getPassword())
                .baselineOnMigrate(true)
                .locations("classpath:/db/migration", "classpath:/db/test_migration")
                .load()
                .migrate();
    }

//    @AfterEach
//    public void cleanupDb() {
//        // Configure Flyway
//        Flyway flyway = Flyway.configure()
//                .dataSource(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())
//                .load();
//
//        // Cleans the schema
//        flyway.clean();
//        // Optionally re-migrate to restore a baseline state
//        flyway.migrate();
//    }

    @AfterAll
    static void cleanup() {
        postgreSQLContainer.stop();
    }

}
