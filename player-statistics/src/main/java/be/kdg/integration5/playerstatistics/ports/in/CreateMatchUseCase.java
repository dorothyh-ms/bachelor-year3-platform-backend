package be.kdg.integration5.playerstatistics.ports.in;

import be.kdg.integration5.playerstatistics.ports.in.commands.CreateMatchCommand;

public interface CreateMatchUseCase {

    public void createMatch(CreateMatchCommand createMatchCommand);
}
