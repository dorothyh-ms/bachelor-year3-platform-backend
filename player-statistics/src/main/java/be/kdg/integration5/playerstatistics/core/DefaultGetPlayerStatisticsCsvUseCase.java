package be.kdg.integration5.playerstatistics.core;


import be.kdg.integration5.common.domain.PlayerStatistics;
import be.kdg.integration5.playerstatistics.ports.in.ExportPlayerStatisticsCsvUseCase;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import be.kdg.integration5.playerstatistics.ports.out.PlayerStatisticsLoadPort;

@Service
public class DefaultGetPlayerStatisticsCsvUseCase implements ExportPlayerStatisticsCsvUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGetPlayerStatisticsCsvUseCase.class);
   private final PlayerStatisticsLoadPort playerStatisticsLoadPort;


    public DefaultGetPlayerStatisticsCsvUseCase(PlayerStatisticsLoadPort playerStatisticsLoadPort) {
        this.playerStatisticsLoadPort = playerStatisticsLoadPort;
    }



    @Override
    public byte[] exportPlayerStatisticsToCSV() {
        LOGGER.info("DefaultGetPlayerStatisticsCsvUseCase is running exportPlayerStatisticsToCSV");
        List<PlayerStatistics> playerStatisticsList = playerStatisticsLoadPort.loadPlayerStatistics();
        LOGGER.info("DefaultGetPlayerStatisticsCsvUseCase is retrieving {}", playerStatisticsList);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
            CSVWriter writer = new CSVWriter(new OutputStreamWriter(out))) {
            String[] header = {"Player Name", "Birth Date", "City", "Country", "BoardGame Title", "Total Time Spent", "Wins", "Losses"};
            writer.writeNext(header);

            for (PlayerStatistics stat : playerStatisticsList) {
                writer.writeNext(new String[]{
                        stat.getPlayerName(),
                        stat.getBirthDate().toString(),
                        stat.getCity(),
                        stat.getCountry(),
                        stat.getGameTitle(),
                        String.valueOf(stat.getTotalTimeSpent()),
                        String.valueOf(stat.getWins()),
                        String.valueOf(stat.getLosses())
                });
            }

            writer.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
