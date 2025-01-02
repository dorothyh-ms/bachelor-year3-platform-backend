package be.kdg.integration5.common.utilities;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UrlValidator {

    public static boolean isUrlValid(String urlString) {
        try {
            // Create a URI from the URL string
            URI uri = URI.create(urlString);

            // Create an HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create an HttpRequest with the HEAD method (to fetch only headers)
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .build();

            // Send the request and check the response code
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

            // Return true if response code is in the valid range
            return response.statusCode() >= 200 && response.statusCode() < 400;
        } catch (Exception e) {
            // If any exception occurs (invalid URL, network issues, etc.)
            return false;
        }
    }
}

