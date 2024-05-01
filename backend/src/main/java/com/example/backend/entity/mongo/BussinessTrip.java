package com.example.backend.entity.mongo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BussinessTrip extends TypeData{

    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String reason;
}
