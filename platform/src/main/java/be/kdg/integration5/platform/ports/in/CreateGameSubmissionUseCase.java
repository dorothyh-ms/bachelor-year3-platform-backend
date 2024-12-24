package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.adapters.in.web.dtos.GameDto;
import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.ports.in.commands.CreateGameSubmissionCommand;

public interface CreateGameSubmissionUseCase {
    GameSubmission createGameSubmission(CreateGameSubmissionCommand gameSubmissionCommand);
}
