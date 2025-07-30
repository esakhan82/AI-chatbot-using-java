package com.example.chatbot.util;

public class ChatTitleUtil {

    // Utility method to generate a chat title if user doesn't provide one
    public static String generateTitle(String inputTitle) {
        if (inputTitle == null || inputTitle.trim().isEmpty()) {
            return "New Chat " + System.currentTimeMillis();
        }
        return inputTitle.trim();
    }
}
