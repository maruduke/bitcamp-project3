package com.example.backend.dto.Message;

import com.example.backend.entity.maria.User;
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
public class SendMessageDto {

    @NotEmpty
    private String message;

    @NotEmpty
    private String senderEmail;

    @NotEmpty
    private String receiverEmail;

    @NotEmpty
    private String receiverName;
}
