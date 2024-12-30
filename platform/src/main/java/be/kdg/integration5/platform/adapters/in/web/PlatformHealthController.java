package be.kdg.integration5.platform.adapters.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platform-check-health")
public class PlatformHealthController {
    public PlatformHealthController() {
    }

    @GetMapping
    public String getHealth() {
        return "all is well!";

    }
}