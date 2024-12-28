package be.kdg.integration5.adapters.in.web;

import be.kdg.integration5.common.domain.PlayerGameClassification;
import be.kdg.integration5.ports.in.GetPlayerProfileUseCase;
import be.kdg.integration5.ports.in.PlayerLoggedInUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/check-health")
public class HealthController {
    public HealthController() {
    }

    @GetMapping
    public String getHealth() {
        return "all is well!";

    }
}