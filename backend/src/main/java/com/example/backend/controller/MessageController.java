package com.example.backend.controller;

import com.example.backend.dto.Member.MemberDto;
import com.example.backend.dto.Message.ReceivedMessageDto;
import com.example.backend.dto.Message.SendMessageDto;
import com.example.backend.entity.maria.User;
import com.example.backend.service.MemberService;
import com.example.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@CrossOrigin
public class MessageController {

    private final MessageService messageService;
    private final MemberService memberService;

    @Autowired
    public MessageController(MessageService messageService, MemberService memberService) {
        this.messageService = messageService;
        this.memberService = memberService;
    }

    // 멤버리스트 전부 가져오기
    @GetMapping("/send")
    public List<MemberDto> getAllMembers(){
        return memberService.getAllMembers();
    }

    // 메세지 보내기
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@AuthenticationPrincipal User user,
            @RequestBody SendMessageDto sendMessageDto) {
        messageService.sendMessage(user.getEmail(), sendMessageDto);
        return ResponseEntity.ok("Message sent successfully");
    }

    // 나에게 온 받은 쪽지 리스트
    @GetMapping("/receivedList")
    public ResponseEntity<List<ReceivedMessageDto>> getReceivedMessages(@AuthenticationPrincipal User user) {
        String email = user.getEmail();
        List<ReceivedMessageDto> receivedMessageList = messageService.getReceivedMessages(email);
        return ResponseEntity.ok(receivedMessageList);
    }

    // 나에게 온 선택한 쪽지 내용 가져오기
    @GetMapping("/receivedList/{messageId}")
    public ResponseEntity<ReceivedMessageDto> getMessageDetails(@PathVariable Long messageId) {
        ReceivedMessageDto receivedMessageDto = messageService.messageReadDetails(messageId);
        return ResponseEntity.ok(receivedMessageDto);
    }

    // 나에게 온 쪽지 내용 가져와서 확인 클릭시 읽음으로 변환
    @PutMapping("/receivedList/{messageId}/check")
    public ResponseEntity<ReceivedMessageDto> checkMessage(@PathVariable Long messageId) {
        ReceivedMessageDto receivedMessageDto = messageService.messageReadChange(messageId);
        return ResponseEntity.ok(receivedMessageDto);
    }

    // 내가 보낸 쪽지 리스트
    @GetMapping("/sentList")
    public  ResponseEntity<List<SendMessageDto>> getSentMessages(@AuthenticationPrincipal User user) {
        String email = user.getEmail();
        List<SendMessageDto> sentMessageList = messageService.getSendMessages(email);
        return ResponseEntity.ok(sentMessageList);
    }

    // 내가 보낸 선택한 쪽지 내용 가져오기
    @GetMapping("/sentList/{messageId}")
    public ResponseEntity<SendMessageDto> getMessageMyDetails(@PathVariable Long messageId) {
        SendMessageDto sendMessageDto = messageService.messageMyReadDetails(messageId);
        return ResponseEntity.ok(sendMessageDto);
    }

    // 나에게 온 쪽지 내용 가져와서 삭제 클릭시 true 업데이트 후 리스트에서 사라지고 보낸사람 쪽에서도 삭제시 DB완전 삭제
    @PutMapping("/receivedList/{messageId}/removed")
    public ResponseEntity<ReceivedMessageDto> removeMessage(@PathVariable Long messageId) {
        ReceivedMessageDto receivedMessageDto = messageService.deleteReceivedMessage(messageId);
        return ResponseEntity.ok(receivedMessageDto);
    }

    // 내가 보낸 쪽지 내용 가져와서 삭제 클릭시 true 업데이트 후 리스트에서 사라지고 받은사람 쪽에서 삭제시 DB완전 삭제
    @PutMapping("/sentList/{messageId}/removed")
    public ResponseEntity<SendMessageDto> removeMessageMyDetails(@PathVariable Long messageId) {
        SendMessageDto sendMessageDto = messageService.deleteSentMessage(messageId);
        return ResponseEntity.ok(sendMessageDto);
    }

}
