package com.example.backend.dto.template;

import com.example.backend.entity.maria.Document;
import com.example.backend.entity.maria.TaskProgress;
import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.entity.maria.enumData.DocType;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.Gson;
import lombok.*;
import org.springframework.boot.json.GsonJsonParser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(name="VACATION", value= VacationDto.class),
        @JsonSubTypes.Type(name="BUSSINESSTRIP", value= BussinessTripDto.class),
        @JsonSubTypes.Type(name="REPORT", value= ReportDto.class),
        @JsonSubTypes.Type(name="ACCOUNTINGEXPENSE", value= AccountExpenseDto.class),

})
public abstract class TemplateDto {

    private Long writer;
    private DocType type;

    private List<String> refList;
    private List<String> approverList;

    private String title;


    abstract public Template< ? extends TypeData> toTemplateEntity(List<Long> approvers, List<Long> refs);


    public Document toDocumentEntity(String documentId, LocalDate createDate) {
        return Document.builder()
                .documentId(documentId)
                .writer(this.writer)
                .title(this.title)
                .type(this.type)
                .state(DocState.PROCESS_1)
                .createDate(createDate)
                .build();
    }

    public void setRefList(String ref) {
        Gson gson = new Gson();
        this.refList = gson.fromJson(ref, List.class);

    }

    public void setApproverList(String approver) {
        Gson gson = new Gson();
        this.approverList = gson.fromJson(approver, List.class);
    }





}
