package com.example.backend.entity.mongo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BussinessTrip extends TypeData{

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String reason;
}
