package com.example.chatbot.controller;

import com.example.chatbot.dto.SendMessageRequest;
import com.example.chatbot.service.ChatAiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final ChatAiService chatAiService;

    public MessageController(ChatAiService chatAiService) {
        this.chatAiService = chatAiService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody SendMessageRequest req) {
        return chatAiService.getChatbotResponse(req.getMessage());
    }
}
