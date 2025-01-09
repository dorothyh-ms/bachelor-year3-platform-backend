package be.kdg.integration5.chatbot.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ContextConfiguration(initializers = AbstractChatbotTest.ChatbotServiceInitalizer.class)
public abstract class AbstractChatbotTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractChatbotTest.class);
    public static final GenericContainer<?> CHATBOT_SERVICE;

    static {

        CHATBOT_SERVICE = new GenericContainer<>(
                DockerImageName.parse("emrecaylar/my-fastapi-app"))
                .withExposedPorts(8000)
                .waitingFor(
                        Wait.forLogMessage(".*Uvicorn running on http://0.0.0.0:8000.*", 1)
                                .withStartupTimeout(java.time.Duration.ofSeconds(360))
                );
        CHATBOT_SERVICE.start();
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(LOGGER);
        CHATBOT_SERVICE.followOutput(logConsumer);
    }

    @Test
    void containerIsRunning() {
        assertTrue(CHATBOT_SERVICE.isCreated());
        assertTrue(CHATBOT_SERVICE.isRunning());
    }

    public static class ChatbotServiceInitalizer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            String apiUrl =String.format("http://%s:%s/question/", CHATBOT_SERVICE.getHost(), CHATBOT_SERVICE.getMappedPort(8000));
            LOGGER.info("Connecting to chatbot at {}", apiUrl);
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "chatbot.api.url=" + apiUrl
            );
        }
    }

}
