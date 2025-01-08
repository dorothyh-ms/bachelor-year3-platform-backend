package be.kdg.integration5.playerstatistics.ports.in;


public interface RecordNewMatchUseCase {

    public void createMatch(RecordNewMatchCommand recordNewMatchCommand);
}
