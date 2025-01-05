package be.kdg.integration5.playerstatistics.adapters.out.predictions.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "predictionApi", url = "${prediction.api.base-url}")
public interface PredictionFeignClient {

    @PostMapping("/predict-engagement")
    PlayerEngagementPredictionsResponse getPredictions(@RequestBody PlayerEngagementPredictionRequest request);
}