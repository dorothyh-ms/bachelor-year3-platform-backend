package be.kdg.integration5.recommender.adapters.in.dtos;

import be.kdg.integration5.common.domain.GameDifficulty;

import java.util.UUID;

public class GameDto {
    private UUID id;
    private String name;

    public GameDto(UUID id, String name) {
        this.id = id;
        this.name = name;

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

}
