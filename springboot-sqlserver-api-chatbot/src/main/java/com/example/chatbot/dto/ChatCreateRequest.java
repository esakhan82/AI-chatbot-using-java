package com.example.chatbot.dto;

public class ChatCreateRequest {
    private String title;

    public ChatCreateRequest() {}

    public ChatCreateRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
