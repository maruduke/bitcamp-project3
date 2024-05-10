package com.example.backend.dto.Message;

import com.example.backend.entity.maria.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageDto {

    private Long messageId;

    @NotEmpty
    private String message;

    @NotEmpty
    private String senderEmail;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;

    @NotEmpty
    private String receiverEmail;

    @NotEmpty
    private String receiverName;

    private String receiverPosition;
}
