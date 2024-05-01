package com.example.backend.controller;

import com.example.backend.dto.Message.ReceivedMessageDto;
import com.example.backend.dto.Message.SendMessageDto;
import com.example.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // 메세지 보내기
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody SendMessageDto sendMessageDto) {
        messageService.sendMessage(sendMessageDto);
        return ResponseEntity.ok("Message sent successfully");
    }

    // 나에게 온 받은 쪽지 리스트
    @GetMapping("/received")
    public ResponseEntity<List<ReceivedMessageDto>> getReceivedMessages(@RequestParam String receiverEmail) {
        List<ReceivedMessageDto> receivedMessageList = messageService.getReceivedMessages(receiverEmail);
        return ResponseEntity.ok(receivedMessageList);
    }

    // 선택한 쪽지 내용 가져오기
    @GetMapping("/{messageId}")
    public ResponseEntity<ReceivedMessageDto> getMessageDetails(@PathVariable Long messageId) {
        ReceivedMessageDto receivedMessageDto = messageService.messageDetails(messageId);
        return ResponseEntity.ok(receivedMessageDto);
    }

}
