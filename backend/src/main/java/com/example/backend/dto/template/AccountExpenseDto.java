package com.example.backend.dto.template;

import com.example.backend.entity.maria.enumData.DocType;
import com.example.backend.entity.mongo.AccountingExpense;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
public class AccountExpenseDto extends TemplateDto{

    private String category;
    private int cost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expenseDate;


    @Override
    public Template<? extends TypeData> toTemplateEntity(List<Long> approvers, List<Long> refs) {

        AccountingExpense accountingExpense = AccountingExpense.builder()
                .title(this.getTitle())
                .category(this.category)
                .cost(this.cost)
                .expenseDate(this.expenseDate)
                .build();

        return Template.<AccountingExpense>builder()
                .writer(this.getWriter())
                .type(this.getType())
                .refList(refs)
                .approverList(approvers)
                .typeData(accountingExpense)
                .build();
    }

}
