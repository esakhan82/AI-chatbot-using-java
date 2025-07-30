package com.example.chatbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class OpenAiClient {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String API_KEY = "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";

    public OpenAiClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://openrouter.ai/api/v1").build();
    }

    public String sendMessage(String prompt) {
        try {
            String response = webClient.post()
                    .uri("/chat/completions")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + API_KEY)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(String.format("""
                        {
                          "model": "openai/gpt-4o-mini",
                          "messages": [{"role": "user", "content": "%s"}]
                        }
                        """, prompt))
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(30))
                    .retryWhen(
                        Retry.fixedDelay(3, Duration.ofSeconds(2))
                            .filter(throwable -> throwable.getMessage() != null 
                                && throwable.getMessage().contains("429"))
                    )
                    .block();

            // Parse JSON to extract assistant's content
            JsonNode root = objectMapper.readTree(response);
            return root.path("choices").get(0).path("message").path("content").asText();

        } catch (Exception e) {
            return "Error calling OpenRouter API: " + e.getMessage();
        }
    }
}
