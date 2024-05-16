package com.example.backend.repository;

import com.example.backend.entity.maria.Message;
import com.example.backend.entity.maria.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.receiverId = :receiverId AND m.readCheck = false AND m.receiverDelete = false")
    Page<Message> findByNoReadReceiverId(User receiverId, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.receiverId = :receiverId AND m.receiverDelete = false")
    Page<Message> findByReceiverId(User receiverId, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.senderId = :senderId AND m.senderDelete = false")
    Page<Message> findBySenderId(User senderId, Pageable pageable);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.receiverId = :receiverId AND m.readCheck = false AND m.receiverDelete = false")
    int findByCountReceiverId(User receiverId);


    void deleteByMessageId(Long messageId);


}
