package com.example.backend.dto.template;

import com.example.backend.entity.maria.enumData.DocType;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(name="VACATION", value= VacationDto.class),
        @JsonSubTypes.Type(name="BUSSINESSTRIP", value= BussinessTripDto.class),
        @JsonSubTypes.Type(name="REPORT", value= ReportDto.class),
        @JsonSubTypes.Type(name="ACCOUNTINGEXPENSE", value= AccountDto.class),

})

public abstract class TemplateDto {

    private Long writer;
    private DocType type;

    private List<Long> refList;
    private List<Long> approverList;

    abstract public Template< ? extends TypeData> toEntity();
}
