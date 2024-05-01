package com.example.backend.dto.template;

import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import com.example.backend.entity.mongo.Vacation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VacationDto extends TemplateDto{
    private String title;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String reason;

    @Override
    public Template<? extends TypeData> toEntity() {

        Vacation vacation = Vacation.builder()
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .reason(this.reason).build();

        return Template.<Vacation>builder()
                .writer(this.getWriter())
                .type(this.getType())
                .approverList(this.getApproverList())
                .typeData(vacation)
                .build();
    }
}
