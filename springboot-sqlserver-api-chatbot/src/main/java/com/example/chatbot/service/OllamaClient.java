package com.example.chatbot.service;

import com.example.chatbot.exception.OllamaException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class OllamaClient {

    private final WebClient webClient;
    private final String openAiApiUrl;
    private final String apiKey;
    private final String model;

    public OllamaClient(WebClient openAiWebClient,
                        @Value("${openai.api.url}") String openAiApiUrl,
                        @Value("${openai.api.key}") String apiKey,
                        @Value("${openai.model}") String model) {
        this.webClient = openAiWebClient;
        this.openAiApiUrl = openAiApiUrl;
        this.apiKey = apiKey;
        this.model = model;
    }

    public Mono<String> chat(List<Map<String,String>> messages){
        if (apiKey == null || apiKey.isBlank()){
            return Mono.error(new OllamaException("OpenAI API key not set."));
        }
        Map<String,Object> body = new HashMap<>();
        body.put("model", model);
        body.put("messages", messages);
        body.put("temperature", 0.7);

        return webClient.post()
                .uri(openAiApiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + apiKey)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(Map.class)
                .map(resp -> {
                    List<?> choices = (List<?>) resp.get("choices");
                    if (choices == null || choices.isEmpty()) return "";
                    Map<?,?> firstChoice = (Map<?,?>) choices.get(0);
                    Map<?,?> message = (Map<?,?>) firstChoice.get("message");
                    return message.get("content").toString();
                });
    }
}
