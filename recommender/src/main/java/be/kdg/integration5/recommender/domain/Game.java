package be.kdg.integration5.recommender.domain;

import be.kdg.integration5.common.domain.GameDifficulty;

import java.util.UUID;

public class Game {
    private UUID id;
    private String name;


    public Game(UUID id, String name) {
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
