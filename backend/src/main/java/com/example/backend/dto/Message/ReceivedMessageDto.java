package com.example.backend.dto.Message;

import com.example.backend.entity.maria.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceivedMessageDto {

    @NotEmpty
    private Long messageId;

    @NotEmpty
    private String senderEmail;

    @NotEmpty
    private String message;

    @NotEmpty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;

    @NotEmpty
    private String senderName;

    private String senderPosition;

    @NotEmpty
    private boolean readCheck;


}
