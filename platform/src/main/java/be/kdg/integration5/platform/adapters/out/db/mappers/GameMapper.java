package be.kdg.integration5.platform.adapters.out.db.mappers;

import be.kdg.integration5.platform.adapters.in.web.dtos.NewGameSubmissionDto;
import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.GameSubmissionJpaEntity;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.domain.SubmissionState;
import be.kdg.integration5.platform.ports.in.commands.CreateGameSubmissionCommand;

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
                addedBy
        );
    }
    // Convert CreateGameSubmissionCommand to GameSubmission
    public static GameSubmission toGameSubmission(CreateGameSubmissionCommand command, UUID id, SubmissionState submissionState) {
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
                submissionState
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

    // Convert CreateGameSubmissionCommand to GameSubmission
    public static NewGameSubmissionDto toGameSubmissionDto(GameSubmission game) {
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
                game.getSubmissionState()
        );
    }
}
