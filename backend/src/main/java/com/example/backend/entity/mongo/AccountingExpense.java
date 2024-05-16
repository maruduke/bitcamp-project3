package com.example.backend.entity.mongo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AccountingExpense extends TypeData{

    private String title;
    private String category;
    private int cost;
    private LocalDate expenseDate;


}
