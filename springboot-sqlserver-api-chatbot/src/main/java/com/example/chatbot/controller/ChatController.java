package com.example.chatbot.controller;

import com.example.chatbot.dto.SendMessageRequest;
import com.example.chatbot.service.ChatAiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatAiService chatAiService;

    public ChatController(ChatAiService chatAiService) {
        this.chatAiService = chatAiService;
    }

    /**
     * Endpoint to send a message to the chatbot.
     * Example: POST /api/chat/send
     */
    @PostMapping("/send")
    public String sendMessage(@RequestBody SendMessageRequest request) {
        return chatAiService.getChatbotResponse(request.getMessage());
    }

    /**
     * Test endpoint
     * Example: GET /api/chat/test
     */
    @GetMapping("/test")
    public String testChat() {
        return "ChatController is working!";
    }
}
