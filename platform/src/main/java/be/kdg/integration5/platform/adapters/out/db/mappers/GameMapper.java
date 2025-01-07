package be.kdg.integration5.platform.adapters.out.db.mappers;

import be.kdg.integration5.platform.adapters.in.web.dtos.GameSubmissionDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.NewGameSubmissionDto;
import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.GameSubmissionJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.util.TemporaryFileManager;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.domain.SubmissionState;
import be.kdg.integration5.platform.ports.in.commands.CreateGameSubmissionCommand;

import java.io.IOException;
import java.util.UUID;

public class GameMapper {
    // Convert GameJpaEntity to Game
    public static Game toGame(GameJpaEntity gameJpaEntity) {
        if (gameJpaEntity == null) {
            return null;
        }
        return new Game(
                gameJpaEntity.getId(),
                gameJpaEntity.getName(),
                gameJpaEntity.getGenre(),
                gameJpaEntity.getDifficultyLevel(),
                gameJpaEntity.getPrice(),
                gameJpaEntity.getDescription(),
                gameJpaEntity.getImage(),
                gameJpaEntity.getUrl()
        );
    }

    public static GameJpaEntity toGameJpaEntity(Game game){
        if (game == null) {
            return null;
        }
        return new GameJpaEntity(
                game.getId(),
                game.getName(),
                game.getGenre(),
                game.getDifficultyLevel(),
                game.getPrice(),
                game.getDescription(),
                game.getImage(),
                game.getUrl()
        );
    }

    public static CreateGameSubmissionCommand toCreateGameSubmissionCommand(NewGameSubmissionDto gameDto, UUID addedBy) {
        if (gameDto == null) {
            return null;
        }
        return new CreateGameSubmissionCommand(
                gameDto.getName(),
                gameDto.getGenre(),
                gameDto.getDifficultyLevel(),
                gameDto.getPrice(),
                gameDto.getDescription(),
                gameDto.getImage(),
                gameDto.getUrl(),
                addedBy,
                gameDto.getFile()
        );
    }
    // Convert CreateGameSubmissionCommand to GameSubmission
    public static GameSubmission toGameSubmission(CreateGameSubmissionCommand command, String fileName, UUID id, SubmissionState submissionState) {
        if (command == null) {
            return null;
        }
        return new GameSubmission(
                id,
                command.name(),
                command.genre(),
                command.difficultyLevel(),
                command.price(),
                command.description(),
                command.image(),
                command.url(),
                submissionState,
                command.addedBy(),
                fileName
        );
    }
    // Convert GameSubmission to GameSubmissionJpaEntity
    public static GameSubmissionJpaEntity toGameSubmissionJpaEntity(GameSubmission submission) {
        if (submission == null) {
            return null;
        }
        return new GameSubmissionJpaEntity(
                submission.getId(),
                submission.getName(),
                submission.getGenre(),
                submission.getDifficultyLevel(),
                submission.getPrice(),
                submission.getDescription(),
                submission.getImage(),
                submission.getUrl(),
                submission.getSubmissionState()
        );
    }
    public static GameSubmission toGameSubmissionEntity(GameSubmissionJpaEntity submissionJpaEntity) {
        if (submissionJpaEntity == null) {
            return null;
        }

            return new GameSubmission(
                    submissionJpaEntity.getGameId(),
                    submissionJpaEntity.getGameName(),
                    submissionJpaEntity.getGenre(),
                    submissionJpaEntity.getDifficultyLevel(),
                    submissionJpaEntity.getPrice(),
                    submissionJpaEntity.getDescription(),
                    submissionJpaEntity.getImage(),
                    submissionJpaEntity.getUrl(),
                    submissionJpaEntity.getSubmissionState(),
                    submissionJpaEntity.getCreatedBy(),
                    submissionJpaEntity.getRulesName()
            );
    }

    // Convert CreateGameSubmissionCommand to GameSubmission
    public static NewGameSubmissionDto toNewGameSubmissionDto(GameSubmission game) {
        if (game == null) {
            return null;
        }
        return new NewGameSubmissionDto(
                game.getId(),
                game.getName(),
                game.getGenre(),
                game.getDifficultyLevel(),
                game.getPrice(),
                game.getDescription(),
                game.getImage(),
                game.getUrl(),
                game.getSubmissionState(),
                game.getCreatedBy()
        );
    }

    public static GameSubmissionDto toGameSubmissionDto(GameSubmission game) {
        if (game == null) {
            return null;
        }
        return new GameSubmissionDto(
                game.getId(),
                game.getName(),
                game.getGenre(),
                game.getDifficultyLevel(),
                game.getPrice(),
                game.getDescription(),
                game.getImage(),
                game.getUrl(),
                game.getSubmissionState()
        );
    }
}
