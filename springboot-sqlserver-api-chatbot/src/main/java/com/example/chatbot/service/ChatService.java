package com.example.chatbot.service;

import com.example.chatbot.dto.ChatCreateRequest;
import com.example.chatbot.entity.Chat;
import com.example.chatbot.repository.ChatRepository;
import com.example.chatbot.util.ChatTitleUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat createChat(ChatCreateRequest request) {
        Chat chat = new Chat();
        // Use getTitle() instead of title()
        String generatedTitle = ChatTitleUtil.generateTitle(request.getTitle());
        chat.setTitle(generatedTitle);
        return chatRepository.save(chat);
    }

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Chat getChatById(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat not found with ID: " + id));
    }

    public void deleteChat(Long id) {
        chatRepository.deleteById(id);
    }
}
