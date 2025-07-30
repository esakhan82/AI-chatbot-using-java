package com.example.chatbot.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="chat_id", nullable=false)
    private Chat chat;

    @Column(length=16, nullable=false)
    private String role;

    @Lob
    @Column(columnDefinition="NVARCHAR(MAX)")
    private String content;

    @Column(name="created_at", updatable=false)
    private LocalDateTime createdAt;

    public Message() {}
    public Message(Chat chat, String role, String content){
        this.chat = chat;
        this.role = role;
        this.content = content;
    }

    @PrePersist
    void prePersist(){
        createdAt = LocalDateTime.now();
    }

    // getters and setters
}
