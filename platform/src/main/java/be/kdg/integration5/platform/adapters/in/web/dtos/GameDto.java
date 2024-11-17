package be.kdg.integration5.platform.adapters.in.web.dtos;

import java.util.UUID;

public class GameDto {
    private UUID id;

    private String name;

    public GameDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public GameDto() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
