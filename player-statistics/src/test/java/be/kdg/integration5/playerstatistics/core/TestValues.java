package be.kdg.integration5.playerstatistics.core;

import be.kdg.integration5.common.domain.GameDifficulty;
import be.kdg.integration5.common.domain.GameGenre;
import be.kdg.integration5.playerstatistics.domain.Gender;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestValues {
    public static UUID GAME_ID = UUID.randomUUID();
    public static String GAME_NAME = "Test";
    public static GameGenre GAME_GENRE = GameGenre.CARD_GAME;
    public static GameDifficulty GAME_DIFFICULTY = GameDifficulty.BEGINNER;
    public static BigDecimal GAME_PRICE = BigDecimal.valueOf(10.0);
    public static String GAME_DESCRIPTION = "Test description";
    public static UUID PLAYER1_ID = UUID.randomUUID();
    public static UUID PLAYER2_ID = UUID.randomUUID();
    public static String PLAYER_USERNAME = "test";
    public static String PLAYER_FIRSTNAME = "test";
    public static String PLAYER_LASTNAME = "test";
    public static LocalDate PLAYER_BIRTHDATE = LocalDate.now();
    public static Gender PLAYER_GENDER = Gender.FEMALE;
    public static String CITY_NAME = "Testcity";
    public static String COUNTRY_NAME = "Testcountry";
    public static UUID MATCH_ID = UUID.randomUUID();
    public static LocalDateTime MATCH_START_TIME = LocalDateTime.now();

    public static UUID PLAYER_MATCH_ID1 = UUID.randomUUID();
    public static UUID PLAYER_MATCH_ID2 = UUID.randomUUID();
    public static int PLAYER_MATCH1_TURNS = 1;
    public static int PLAYER_MATCH2_TURNS = 2;
    public static double PLAYER_MATCH1_SCORE = 3;
    public static double PLAYER_MATCH2_SCORE = 4;

}
