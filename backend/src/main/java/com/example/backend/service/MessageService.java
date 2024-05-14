package com.example.backend.service;

import com.example.backend.dto.Message.ReceivedMessageDto;
import com.example.backend.dto.Message.SendMessageDto;
import com.example.backend.entity.maria.Message;
import com.example.backend.entity.maria.User;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    public void sendMessage(String senderEmail, SendMessageDto sendMessageDto){

        User sender = userRepository.findByEmail(senderEmail).orElseThrow(()
        -> new IllegalArgumentException("sender not found"));

        User receiver = userRepository.findByEmail(sendMessageDto.getReceiverEmail())
                .orElseThrow(() -> new IllegalArgumentException("receiver not found"));

        Message message = Message.builder()
                .senderId(sender)
                .receiverId(receiver)
                .message(sendMessageDto.getMessage())
                .sendTime(LocalDateTime.now())
                .build();

        messageRepository.save(message);
    }
    // 받은 쪽지 정보 가져오기
    private ReceivedMessageDto receivedMessageDto(Message message) {
        return ReceivedMessageDto.builder()
                .messageId(message.getMessageId())
                .senderEmail(message.getSenderId().getEmail())
                .senderName(message.getSenderId().getName())
                .senderPosition(message.getSenderId().getPosition())
                .message(message.getMessage())
                .sendTime(message.getSendTime())
                .readCheck(message.isReadCheck())
                .build();
    }

    // 나에게 온 안 읽은 쪽지 리스트 count
    public int getNotReadCountMessages(String receiverEmail){
        User receiverId = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new IllegalStateException("Receiver not found"));
        return messageRepository.findByCountReceiverId(receiverId);
    }

    // 나에게 온 받은 쪽지 리스트
    public Slice<ReceivedMessageDto> getReceivedMessages(String receiverEmail, int pageNumber, int pageSize) {
        User receiverId = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("sendTime").descending());

        Page<Message> messages = messageRepository.findByReceiverId(receiverId, pageRequest);

        List<ReceivedMessageDto> receivedMessageDto = messages.getContent().stream()
                .map(this::receivedMessageDto)
                .collect(Collectors.toList());

        return new SliceImpl<>(receivedMessageDto, messages.getPageable(), messages.hasNext());
    }

    // 나에게 온 받은 안읽음 쪽지 리스트
    public Slice<ReceivedMessageDto> getNoReadReceivedMessages(String receiverEmail, int pageNumber, int pageSize) {
        User receiverId = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("sendTime").descending());

        Page<Message> ReadMessages = messageRepository.findByNoReadReceiverId(receiverId, pageRequest);

        List<ReceivedMessageDto> receivedReadMessageDto = ReadMessages.getContent().stream()
                .map(this::receivedMessageDto)
                .collect(Collectors.toList());

        return new SliceImpl<>(receivedReadMessageDto, ReadMessages.getPageable(), ReadMessages.hasNext());
    }

    // 내가 받은 쪽지 읽기
    public ReceivedMessageDto messageReadDetails(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        return receivedMessageDto(message);
    }

    // 쪽지 읽음으로 변환
    @Transactional
    public ReceivedMessageDto messageReadChange(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        if (!message.isReadCheck()) { // 읽지 않은 경우에만 업데이트
            message.updateReadCheck(true);
            messageRepository.save(message); // 변경된 상태 저장
        }
        return receivedMessageDto(message);
    }

    // 나에게 온 쪽지 리스트에 receiverDelete 값을 true 업데이트
    public ReceivedMessageDto deleteReceivedMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        receiverDeleteChange(message); // 내가 받은 쪽지 리스트 receiverDelete - true 업데이트

        if(message.isReceiverDelete() && message.isSenderDelete()) {
            messageRepository.deleteById(messageId);
        }

        return receivedMessageDto(message);

    }

    // 나에게 온 쪽지 삭제 정보 true로 변환
    @Transactional
    public void receiverDeleteChange(Message message) {
        if (!message.isReceiverDelete()) {
            message.updateReceiverDelete(true); // receiverDelete 값을 true로 업데이트
            messageRepository.save(message);
        }
    }

    // 보낸 쪽지 정보 가져오기
    private SendMessageDto sentMessageDto(Message message) {
        return SendMessageDto.builder()
                .messageId(message.getMessageId())
                .message(message.getMessage())
                .receiverEmail(message.getReceiverId().getEmail())
                .senderEmail(message.getSenderId().getEmail())
                .sendTime(message.getSendTime())
                .receiverName(message.getReceiverId().getName())
                .receiverPosition(message.getReceiverId().getPosition())
                .build();
    }

    // 내가 보낸 쪽지 리스트
    public Slice<SendMessageDto> getSendMessages(String senderEmail, int pageNumber, int pageSize) {
        User senderId = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("sendTime").descending());
        Page<Message> sendMessages = messageRepository.findBySenderId(senderId, pageRequest);

        List<SendMessageDto> sendMessageDto = sendMessages.getContent().stream()
                .map(this::sentMessageDto)
                .collect(Collectors.toList());
        return new SliceImpl<>(sendMessageDto, sendMessages.getPageable(), sendMessages.hasNext());
    }

    // 내가 보낸 쪽지 읽기
    public SendMessageDto messageMyReadDetails(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        return sentMessageDto(message);
    }

    // 내가 보낸 쪽지 리스트에 senderDelete 값을 true 업데이트
    public SendMessageDto deleteSentMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        senderDeleteChange(message); // 내가 보낸 쪽지지 리스트 senderDelete - true 업데이트

        if(message.isSenderDelete() && message.isReceiverDelete()) {
            messageRepository.deleteById(messageId);
        }

        return sentMessageDto(message);
    }

    // 삭제 정보 true로 변환
    @Transactional
    public void senderDeleteChange(Message message) {
        if (!message.isSenderDelete()) {
            message.updateSenderDelete(true); // senderDelete 값을 true로 업데이트
            messageRepository.save(message);
        }
    }
}
