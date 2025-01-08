package be.kdg.integration5.platform.adapters.in.web.dtos;

import be.kdg.integration5.platform.domain.Achievement;
import be.kdg.integration5.platform.domain.Player;

import java.util.UUID;

public class PlayerAchievementDto {
    private UUID id;
    private Achievement achievement;
    private Player player;

    public PlayerAchievementDto(UUID id, Achievement achievement, Player player) {
        this.id = id;
        this.achievement = achievement;
        this.player = player;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
