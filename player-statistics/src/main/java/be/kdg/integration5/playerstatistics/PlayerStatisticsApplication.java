package be.kdg.integration5.playerstatistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlayerStatisticsApplication {
    private static final Logger log = LoggerFactory.getLogger(PlayerStatisticsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PlayerStatisticsApplication.class, args);
    }

}
