package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.ports.out.GameSubmissionLoadPort;

import java.util.List;

public interface GetGameSubmissionsUseCase {

    List<GameSubmission> getGameSubmission();}
