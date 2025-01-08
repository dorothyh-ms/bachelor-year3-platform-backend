package be.kdg.integration5.playerstatistics.adapters.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics-check-health")
public class HealthController {
    public HealthController() {
    }

    @GetMapping
    public String getHealth() {
        return "all is well!";

    }
}