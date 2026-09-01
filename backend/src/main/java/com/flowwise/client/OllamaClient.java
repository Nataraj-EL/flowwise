package com.flowwise.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class OllamaClient {

    private final String ollamaUrl = "http://localhost:11434/api/generate";
    private final String defaultModel = "gemma3:4b";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OllamaClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public Optional<String> generate(String prompt) {
        return generate(defaultModel, prompt);
    }

    public Optional<String> generate(String model, String prompt) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("model", model);
            payload.put("prompt", prompt);
            payload.put("stream", false);

            String requestBody = objectMapper.writeValueAsString(payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ollamaUrl))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(5))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                if (root.has("response")) {
                    return Optional.of(root.get("response").asText());
                }
            }
        } catch (Exception ignored) {
            // Ollama offline or connection refused - return empty to trigger fallback
        }
        return Optional.empty();
    }

    public String getDefaultModel() {
        return defaultModel;
    }
}
