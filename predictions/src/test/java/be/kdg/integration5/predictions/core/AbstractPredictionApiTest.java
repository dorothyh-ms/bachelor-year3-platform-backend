package be.kdg.integration5.predictions.core;


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
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(initializers = AbstractPredictionApiTest.PredictionApiUrlInitializer.class)
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {RabbitAutoConfiguration.class, DataSourceAutoConfiguration.class})
public abstract class AbstractPredictionApiTest {

    public static final GenericContainer<?> PREDICTION_SERVICE;

    static {
        PREDICTION_SERVICE = new GenericContainer<>(
                new ImageFromDockerfile()
                        .withFileFromPath(".", Paths.get("../infrastructure/prediction-api").toAbsolutePath())
        )
                .withExposedPorts(8000)
                .waitingFor(
                        Wait.forLogMessage(".*Uvicorn running on.*", 1)
                                .withStartupTimeout(java.time.Duration.ofSeconds(10))
                );

        PREDICTION_SERVICE.start();
    }


    @Test
    void containerIsRunning() {
        assertTrue(PREDICTION_SERVICE.isCreated());
        assertTrue(PREDICTION_SERVICE.isRunning());

    }

    public static class PredictionApiUrlInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "prediction.api.base-url=" + String.format("http://%s:%s", PREDICTION_SERVICE.getHost(), PREDICTION_SERVICE.getMappedPort(8000))
            );
        }
    }
}


