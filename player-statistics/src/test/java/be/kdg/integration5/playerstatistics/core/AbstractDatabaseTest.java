package be.kdg.integration5.playerstatistics.core;


import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(initializers = AbstractDatabaseTest.DataSourceInitializer.class)
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {RabbitAutoConfiguration.class})
public abstract class AbstractDatabaseTest {

    @Autowired
    private BoardGameRepository boardGameRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayerProfileRepository playerRepository;

    @Autowired
    private PlayerMatchRepository playerMatchRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private LocationRepository locationRepository;

    public static final MySQLContainer<?> DATABASE;


    static {
        DATABASE = new MySQLContainer<>("mysql:8.0.30")
                .withUsername("root")
                .withPassword("")
                .withPrivilegedMode(true);
        DATABASE.withInitScript("initScript.sql");
        DATABASE.start();
    }

    @Test
    void containerIsRunning() {
        assertTrue(DATABASE.isCreated());
        assertTrue(DATABASE.isRunning());
    }

    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            System.out.println(DATABASE.getUsername());
            System.out.println(DATABASE.getPassword());
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + DATABASE.getJdbcUrl(),
                    "spring.datasource.username=" + DATABASE.getUsername(),
                    "spring.datasource.password=" + DATABASE.getPassword()
            );


        }
    }

    @AfterEach
    void teardown() {
        playerMatchRepository.deleteAll();;
        matchRepository.deleteAll();
        playerRepository.deleteAll();
        locationRepository.deleteAll();;
        countryRepository.deleteAll();
        boardGameRepository.deleteAll();
    }

}


