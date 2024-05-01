package com.example.backend.service;

import com.example.backend.dto.Message.ReceivedMessageDto;
import com.example.backend.dto.Message.SendMessageDto;
import com.example.backend.entity.maria.Message;
import com.example.backend.entity.maria.User;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    // 메세지 보내기
    public void sendMessage(SendMessageDto sendMessageDto){

        User sender = userRepository.findByEmail((sendMessageDto.getSenderEmail()))
                .orElseThrow(() -> new IllegalArgumentException("sender not found"));
        User receiver = userRepository.findByEmail((sendMessageDto.getReceiverEmail()))
                .orElseThrow(() -> new IllegalArgumentException("receiver not found"));

        Message message = Message.builder()
                .senderId(sender)
                .receiverId(receiver)
                .message(sendMessageDto.getMessage())
                .build();

        messageRepository.save(message);


    }
    // 받은 쪽지 정보 가져오기
    private ReceivedMessageDto receivedMessageDto(Message message) {
        return ReceivedMessageDto.builder()
                .messageId(message.getMessageId())
                .senderEmail(message.getSenderId().getEmail())
                .senderName(message.getSenderId().getName())
                .message(message.getMessage())
                .sendTime(message.getSendTime())
                .readCheck(message.isReadCheck())
                .build();
    }

    // 나에게 온 받은 쪽지 리스트
    public List<ReceivedMessageDto> getReceivedMessages(String receiverEmail) {
        User receiverId = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        List<Message> messages = messageRepository.findByReceiverId(receiverId);

        return messages.stream()
                .map(this::receivedMessageDto)
                .collect(Collectors.toList());
    }

    // 쪽지 읽기
    public ReceivedMessageDto messageDetails(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        messageReadChange(message); // 읽은 쪽지로 표시

        return receivedMessageDto(message);
    }

    // 쪽지 읽음으로 변환
    @Transactional
    public void messageReadChange(Message message) {
        if (!message.isReadCheck()) { // 읽지 않은 경우에만 업데이트
            message.updateReadCheck(true);
            messageRepository.save(message); // 변경된 상태 저장
        }
    }

    // 내가 보낸 쪽지 리스트
    public List<SendMessageDto> getSendMessages(String senderEmail) {
        User senderId = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        List<Message> sendMessages = messageRepository.findBySenderId(senderId);

        return sendMessages.stream()
                .map(this::sentMessageDto)
                .collect(Collectors.toList());
    }

    // 보낸 쪽지 정보 가져오기
    private SendMessageDto sentMessageDto(Message message) {
        return SendMessageDto.builder()
                .message(message.getMessage())
                .receiverEmail(message.getReceiverId().getEmail())
                .senderEmail(message.getSenderId().getEmail())
                .receiverName(message.getReceiverId().getName())
                .receiveTime(LocalDateTime.now())
                .build();
    }
}
