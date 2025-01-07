package be.kdg.integration5.platform.domain;

import be.kdg.integration5.common.domain.GameDifficulty;
import be.kdg.integration5.common.domain.GameGenre;
import be.kdg.integration5.common.utilities.UrlValidator;

import java.math.BigDecimal;
import java.util.UUID;

public class GameSubmission extends Game{

    private SubmissionState submissionState;
    private UUID createdBy;
    private String fileName;

    public GameSubmission(UUID id, String name, GameGenre genre, GameDifficulty difficultyLevel, BigDecimal price, String description, String image, String url, SubmissionState submissionState, UUID createdBy, String file) {
        super(id, name, genre, difficultyLevel, price, description, image, url);
        this.submissionState = submissionState;
        this.createdBy = createdBy;
        this.fileName = file;
    }


    public SubmissionState getSubmissionState() {
        return submissionState;
    }

    public void setSubmissionState(SubmissionState submissionState) {
        this.submissionState = submissionState;
    }

    public boolean isValidUrl(){
        return UrlValidator.isUrlValid(this.getUrl());
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "GameSubmission{" +
                "submissionState=" + submissionState +
                ", createdBy=" + createdBy +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
