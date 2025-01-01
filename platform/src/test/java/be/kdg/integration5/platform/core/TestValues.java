package be.kdg.integration5.platform.core;

import be.kdg.integration5.common.domain.GameDifficulty;
import be.kdg.integration5.common.domain.GameGenre;
import be.kdg.integration5.platform.domain.Game;

import java.math.BigDecimal;
import java.util.UUID;

public class TestValues {
    public static final UUID PLAYER1_ID = UUID.randomUUID();
    public static final String PLAYER1_USERNAME = "player1";
    public static final UUID PLAYER2_ID = UUID.randomUUID();
    public static final String PLAYER2_USERNAME = "player2";

    public static final UUID GAME_ID = UUID.randomUUID();
    public static final String GAME_NAME = "Test";
    public static final GameGenre GENRE = GameGenre.CARD_GAME;
    public static final GameDifficulty DIFFICULTY = GameDifficulty.BEGINNER;
    public static final String GAME_DESCRIPTION = "Test";
    public static final BigDecimal GAME_PRICE = BigDecimal.valueOf(1);
    public static final String GAME_IMAGE = "Test";
    public static final String GAME_URL="Test";

    public static final UUID LOBBY_ID = UUID.randomUUID();
    public static final UUID INVITE_ID = UUID.randomUUID();

    public static final Game GAME = new Game(
            GAME_ID,
            GAME_NAME,
            GENRE,
            GameDifficulty.BEGINNER,
            GAME_PRICE,
            GAME_DESCRIPTION,
            GAME_IMAGE,
            GAME_URL
            );


}
