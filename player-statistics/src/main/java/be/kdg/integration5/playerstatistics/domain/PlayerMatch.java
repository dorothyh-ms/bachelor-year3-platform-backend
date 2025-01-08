package be.kdg.integration5.playerstatistics.domain;

import be.kdg.integration5.common.domain.MatchOutcome;

import java.util.UUID;

public class PlayerMatch {
    private UUID id;

    private PlayerProfile player;

    private int numberOfTurns;

    private double score;


    private MatchOutcome outcome;

    public PlayerMatch(UUID id, PlayerProfile player, int numberOfTurns, double score, MatchOutcome outcome) {
        this.id = id;
        this.player = player;
        this.numberOfTurns = numberOfTurns;
        this.score = score;
        this.outcome = outcome;
    }

    public PlayerMatch(PlayerProfile player) {
        this.id = UUID.randomUUID();
        this.player = player;
        this.numberOfTurns = 0;
        this.score = 0;
        this.outcome = MatchOutcome.IN_PROGRESS;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PlayerProfile getPlayer() {
        return player;
    }

    public void setPlayer(PlayerProfile player) {
        this.player = player;
    }

    public void incrementNumberOfTurns(){
        this.numberOfTurns++;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public MatchOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(MatchOutcome outcome) {
        this.outcome = outcome;
    }

    public double updateScore(double points){
        this.score+= points;
        return this.score;
    }
}
