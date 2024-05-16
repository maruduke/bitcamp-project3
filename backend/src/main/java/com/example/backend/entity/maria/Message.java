package com.example.backend.entity.maria;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="message_id")
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="senderId")
    private User senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverId")
    private User receiverId;

    @Column(nullable = false)
    private String message;

    @CreatedDate
    private LocalDateTime sendTime;

    @ColumnDefault("false")
    private boolean readCheck;

    @ColumnDefault("false")
    private boolean senderDelete;

    @ColumnDefault("false")
    private boolean receiverDelete;

    @Builder
    public Message(User senderId, User receiverId, String message, LocalDateTime sendTime) {

        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sendTime = sendTime;
    }

    public void updateReadCheck(boolean readCheck) {
        this.readCheck = readCheck;
    }

    public void updateSenderDelete(boolean senderDelete) {
        this.senderDelete = senderDelete;
    }

    public void updateReceiverDelete(boolean receiverDelete) {
        this.receiverDelete = receiverDelete;
    }

}
