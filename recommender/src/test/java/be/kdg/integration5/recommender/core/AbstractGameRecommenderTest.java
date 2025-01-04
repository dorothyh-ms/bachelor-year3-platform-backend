package be.kdg.integration5.recommender.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(initializers = AbstractGameRecommenderTest.GameRecommenderInitializer.class)
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public abstract class AbstractGameRecommenderTest {
    public static final GenericContainer<?> GAME_RECOMMENDER_SERVICE;

    static {

        GAME_RECOMMENDER_SERVICE = new GenericContainer(
                new ImageFromDockerfile()
                        .withFileFromPath(".", Paths.get("../infrastructure/game-recommender-api").toAbsolutePath())
        )
                .withExposedPorts(8000)
                .waitingFor(
                        Wait.forLogMessage(".*Uvicorn running on.*", 1)
                                .withStartupTimeout(java.time.Duration.ofSeconds(10))
                );
        ;
        GAME_RECOMMENDER_SERVICE.start();
    }

    @Test
    void containerIsRunning() {
        assertTrue(GAME_RECOMMENDER_SERVICE.isCreated());
        assertTrue(GAME_RECOMMENDER_SERVICE.isRunning());
    }

    public static class GameRecommenderInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "game.recommender.base-url=" + String.format("http://%s:%s", GAME_RECOMMENDER_SERVICE.getHost(), GAME_RECOMMENDER_SERVICE.getMappedPort(8000))
            );
        }
    }

    @BeforeEach
    void setup() {

    }
}



