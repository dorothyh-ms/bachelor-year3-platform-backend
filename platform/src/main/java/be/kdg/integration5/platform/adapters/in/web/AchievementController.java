package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.platform.adapters.in.web.dtos.GameDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.PlayerAchievementDto;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.mappers.PlayerMapper;
import be.kdg.integration5.platform.domain.Achievement;
import be.kdg.integration5.platform.domain.PlayerAchievement;
import be.kdg.integration5.platform.ports.in.GetAchievementsOfPlayerUseCase;
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
@RequestMapping("/achievement")
public class AchievementController {
    private final GetAchievementsOfPlayerUseCase defaultGetAchievementsOfPlayerUseCase;

    public AchievementController(GetAchievementsOfPlayerUseCase defaultGetAchievementsOfPlayerUseCase) {
        this.defaultGetAchievementsOfPlayerUseCase = defaultGetAchievementsOfPlayerUseCase;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<PlayerAchievementDto>> getAchievementsOfPlayer(@AuthenticationPrincipal Jwt token) {

        UUID userId = UUID.fromString(token.getClaimAsString("sub"));
        List<PlayerAchievement> receivedAchievements = defaultGetAchievementsOfPlayerUseCase.getPlayerAchievements(userId);
        if (!receivedAchievements.isEmpty()) {
            return new ResponseEntity<>(receivedAchievements.stream().map(achievement -> new PlayerAchievementDto(
                    achievement.getId(),
                    new Achievement(
                            achievement.getAchievement().getId(),
                            achievement.getAchievement().getName(),
                            achievement.getAchievement().getDescription(),
                            achievement.getAchievement().getGame()
                    ),
                    achievement.getPlayer())).toList(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
