package be.kdg.integration5.common.domain;

import java.time.LocalDate;

public class PlayerStatistics {

    private String playerName;
    private LocalDate birthDate;
    private Gender gender;
    private String gameTitle;
    private double totalTimeSpent;
    private double wins;
    private double losses;
    private String city;
    private String country;

    public PlayerStatistics(String playerName, LocalDate birthDate, String city, String country, String gameTitle, double totalTimeSpent, double wins, double losses) {
        this.playerName = playerName;
        this.birthDate = birthDate;
        this.city=city;
        this.country=country;
        this.gameTitle = gameTitle;
        this.totalTimeSpent = totalTimeSpent;
        this.wins = wins;
        this.losses = losses;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public double getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public void setTotalTimeSpent(double totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }

    public double getWins() {
        return wins;
    }

    public void setWins(double wins) {
        this.wins = wins;
    }

    public double getLosses() {
        return losses;
    }

    public void setLosses(double losses) {
        this.losses = losses;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "PlayerStatistics{" +
                "playerName='" + playerName + '\'' +
                ", gameTitle='" + gameTitle + '\'' +
                ", totalTimeSpent=" + totalTimeSpent +
                ", wins=" + wins +
                ", losses=" + losses +
                '}';
    }
}
