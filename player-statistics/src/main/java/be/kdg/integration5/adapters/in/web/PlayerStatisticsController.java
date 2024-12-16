package be.kdg.integration5.adapters.in.web;


import be.kdg.integration5.adapters.in.web.dto.PlayerStatisticsDto;
import be.kdg.integration5.common.domain.PlayerGameClassification;
import be.kdg.integration5.common.domain.PlayerStatistics;
import be.kdg.integration5.ports.in.ExportPlayerStatisticsCsvUseCase;
import be.kdg.integration5.ports.in.GetPlayerStatisticsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/player-statistics")
public class PlayerStatisticsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerStatisticsController.class);

    private final ExportPlayerStatisticsCsvUseCase exportPlayerStatisticsCsvUseCase;
    private final GetPlayerStatisticsUseCase getPlayerStatisticsUseCase;

    public PlayerStatisticsController(ExportPlayerStatisticsCsvUseCase exportPlayerStatisticsCsvUseCase, GetPlayerStatisticsUseCase getPlayerStatisticsUseCase) {
        this.exportPlayerStatisticsCsvUseCase = exportPlayerStatisticsCsvUseCase;
        this.getPlayerStatisticsUseCase = getPlayerStatisticsUseCase;
    }


    @GetMapping()
    public ResponseEntity<List<PlayerStatisticsDto>> getPlayerStatistics(@AuthenticationPrincipal Jwt token) throws IOException {
        LOGGER.info("PlayerStatisticsController is running getPlayerStatistics");
        List<PlayerStatistics> playerStatisticsList = getPlayerStatisticsUseCase.getPlayerStatistics();

        return new ResponseEntity<>(playerStatisticsList
                        .stream()
                        .map(
                playerStatistics -> new PlayerStatisticsDto(
                        playerStatistics.getPlayerName(),
                        playerStatistics.getBirthDate(),
                        null,
                        playerStatistics.getCity(),
                        playerStatistics.getCountry(),
                        playerStatistics.getGameTitle(),
                        playerStatistics.getTotalTimeSpent(),
                        playerStatistics.getWins(),
                        playerStatistics.getLosses()
                )
                ).toList(),
                HttpStatus.OK);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportPlayerStatistics(@AuthenticationPrincipal Jwt token) throws IOException {
        LOGGER.info("PlayerStatisticsController is running exportPlayerStatistics");
        byte[] csvData = exportPlayerStatisticsCsvUseCase.exportPlayerStatisticsToCSV();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=player_statistics.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(csvData);
    }
}
