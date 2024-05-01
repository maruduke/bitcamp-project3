package com.example.backend.dto.template;

import com.example.backend.entity.maria.enumData.DocType;
import com.example.backend.entity.mongo.AccountingExpense;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
public class AccountExpenseDto extends TemplateDto{

    private String category;
    private int cost;
    private LocalDateTime expenseDate;


    @Override
    public Template<? extends TypeData> toTemplateEntity() {

        AccountingExpense accountingExpense = AccountingExpense.builder()
                .title(this.getTitle())
                .category(this.category)
                .cost(this.cost)
                .expenseDate(this.expenseDate)
                .build();


        return Template.<AccountingExpense>builder()
                .writer(this.getWriter())
                .type(this.getType())
                .refList(this.getRefList())
                .approverList(this.getApproverList())
                .typeData(accountingExpense)
                .build();
    }
}
