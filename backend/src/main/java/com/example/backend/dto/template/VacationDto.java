package com.example.backend.dto.template;

import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import com.example.backend.entity.mongo.Vacation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString(callSuper = true)
public class VacationDto extends TemplateDto{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String reason;

    @Override
    public Template<? extends TypeData> toTemplateEntity(List<Long> approvers, List<Long> refs) {

        Vacation vacation = Vacation.builder()
                .title(this.getTitle())
                .startDate(this.startDate)
                .endDate(this.endDate)
                .reason(this.reason).build();

        return Template.<Vacation>builder()
                .writer(this.getWriter())
                .type(this.getType())
                .refList(refs)
                .approverList(approvers)
                .typeData(vacation)
                .build();
    }
}
