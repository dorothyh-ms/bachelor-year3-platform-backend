package be.kdg.integration5.adapters.in.dtos;

import be.kdg.integration5.common.domain.GameDifficulty;

import java.util.UUID;

public class GameDto {
    private UUID id;
    private String name;
    private GameDifficulty difficulty;
    private String description;

    public GameDto(UUID id, String name, GameDifficulty difficulty, String description) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
