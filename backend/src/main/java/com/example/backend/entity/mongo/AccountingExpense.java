package com.example.backend.entity.mongo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class AccountingExpense extends TypeData{

    private String title;
    private String category;
    private int cost;
    private LocalDateTime expenseDate;
}
