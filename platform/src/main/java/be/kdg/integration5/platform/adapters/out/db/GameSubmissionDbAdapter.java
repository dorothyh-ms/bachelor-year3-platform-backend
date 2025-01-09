package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.GameSubmissionRepository;
import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.domain.SubmissionState;
import be.kdg.integration5.platform.ports.out.GameSubmissionLoadPort;
import be.kdg.integration5.platform.ports.out.GameSubmissionRulesSavePort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GameSubmissionDbAdapter implements GameSubmissionLoadPort, GameSubmissionRulesSavePort {

    private final GameSubmissionRepository gameSubmissionRepository;

    public GameSubmissionDbAdapter(GameSubmissionRepository gameSubmissionRepository) {
        this.gameSubmissionRepository = gameSubmissionRepository;
    }

    @Override
    public List<GameSubmission> loadAllGameSubmissions() {
        List<GameSubmission> list = new ArrayList<>();
        gameSubmissionRepository.findAll().forEach(game -> list.add(GameMapper.toGameSubmissionEntity(game)));
        return list;
    }

    @Override
    public List<GameSubmission> loadPendingGameSubmissions() {
        List<GameSubmission> list = new ArrayList<>();
        gameSubmissionRepository.getAllBySubmissionStateIs(SubmissionState.IN_PROGRESS).forEach(game -> list.add(GameMapper.toGameSubmissionEntity(game)));
        return list;
    }

    @Override
    public List<GameSubmission> loadDeniedGameSubmissions() {
        List<GameSubmission> list = new ArrayList<>();
        gameSubmissionRepository.getAllBySubmissionStateIs(SubmissionState.DENIED).forEach(game -> list.add(GameMapper.toGameSubmissionEntity(game)));
        return list;
    }

    @Override
    public void saveGameRules(String fileName) {
        // nothing to implement
    }
}
