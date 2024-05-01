package com.example.backend.repository;

import com.example.backend.entity.maria.Message;
import com.example.backend.entity.maria.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiverId(User receiverId);

}
