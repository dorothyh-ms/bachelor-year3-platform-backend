package be.kdg.integration5.platform.domain;

import java.util.UUID;

public class Achievement {
    private UUID id;
    private String name;
    private String description;
    private Game game;

    public Achievement(UUID id, String name, String description, Game game) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Achievement{" +

                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", game=" + game +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
