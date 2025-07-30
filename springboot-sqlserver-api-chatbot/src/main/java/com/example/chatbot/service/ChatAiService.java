package com.example.chatbot.service;

import org.springframework.stereotype.Service;

@Service
public class ChatAiService {

    private final OpenAiClient openAiClient;

    public ChatAiService(OpenAiClient openAiClient) {
        this.openAiClient = openAiClient;
    }

    public String getChatbotResponse(String prompt) {
        return openAiClient.sendMessage(prompt);
    }
}
