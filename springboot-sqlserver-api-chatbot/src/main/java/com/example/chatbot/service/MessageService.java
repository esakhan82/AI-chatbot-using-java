package com.example.chatbot.service;

import com.example.chatbot.entity.Chat;
import com.example.chatbot.entity.Message;
import com.example.chatbot.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Chat chat, String role, String content) {
        Message msg = new Message(chat, role, content);
        return messageRepository.save(msg);
    }

    public List<Message> getMessagesByChatId(Long chatId) {
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chatId);
    }
}
