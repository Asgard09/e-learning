package com.example.AsgardShop.model;

import com.example.AsgardShop.base.MessageStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "content")
    private String content;

    @Column(name = "sent_at")
    private Date sentAt;

    @Column(name = "chat_room_id")
    private Long chatRoomId;

    @Column(name = "status")
    private MessageStatus messageStatus;
}
