package be.kdg.integration5.predictions.adapters.out.predictions;

public class FeignClientErrorException extends RuntimeException {
    public FeignClientErrorException(String message) {
        super(message);
    }
}
