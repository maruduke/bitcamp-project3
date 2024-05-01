package com.example.backend.entity.mongo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Vacation {

    private String title;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String reason;
}
