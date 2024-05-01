package com.example.backend.dto.template;

import com.example.backend.entity.mongo.Report;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
public class ReportDto extends TemplateDto{
    private String title;
    private String detail;

    @Override
    public Template<? extends TypeData> toEntity() {
        Report report = Report.builder()
                .title(title)
                .detail(detail)
                .build();

        return Template.<Report>builder()
                .writer(this.getWriter())
                .type(this.getType())
                .refList(this.getRefList())
                .typeData(report)
                .build();
    }
}
