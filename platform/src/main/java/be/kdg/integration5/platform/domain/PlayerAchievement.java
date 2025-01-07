package be.kdg.integration5.platform.domain;

import java.util.UUID;

public class PlayerAchievement {
    private UUID id;
    private Achievement achievement;
    private Player player;

    public PlayerAchievement(Achievement achievement, Player player) {
        this.id = UUID.randomUUID();
        this.achievement = achievement;
        this.player = player;
    }

    public PlayerAchievement(UUID id, Achievement achievement, Player player) {
        this.id = id;
        this.achievement = achievement;
        this.player = player;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
