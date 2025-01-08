package be.kdg.integration5.playerstatistics.ports.in;

import be.kdg.integration5.playerstatistics.ports.in.commands.EndMatchCommand;

public interface EndMatchUseCase {
    public void endMatch(EndMatchCommand command);
}
