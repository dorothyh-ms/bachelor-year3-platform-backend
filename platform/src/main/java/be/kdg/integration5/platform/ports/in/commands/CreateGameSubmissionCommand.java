package be.kdg.integration5.platform.ports.in.commands;

import be.kdg.integration5.common.domain.GameDifficulty;
import be.kdg.integration5.common.domain.GameGenre;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateGameSubmissionCommand(String name,
                                          GameGenre genre,
                                          GameDifficulty difficultyLevel,
                                          BigDecimal price,
                                          String description,
                                          String image,
                                          String url,
                                          UUID addedBy){
}
