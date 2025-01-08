package be.kdg.integration5.playerstatistics.ports.in;

import be.kdg.integration5.playerstatistics.ports.in.commands.ProjectPlayerMatchCommand;

public interface PlayerMatchProjector {
    void projectPlayerMatch(ProjectPlayerMatchCommand command);
}
