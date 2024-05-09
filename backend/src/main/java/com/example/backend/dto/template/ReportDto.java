package com.example.backend.dto.template;

import com.example.backend.entity.mongo.Report;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter @Setter @ToString(callSuper = true)
public class ReportDto extends TemplateDto{

    private String detail;

    @Override
    public Template<? extends TypeData> toTemplateEntity(List<Long> approvers, List<Long> refs) {
        Report report = Report.builder()
                .title(this.getTitle())
                .detail(detail)
                .build();

        return Template.<Report>builder()
                .writer(this.getWriter())
                .type(this.getType())
                .approverList(approvers)
                .refList(refs)
                .typeData(report)
                .build();
    }
}
