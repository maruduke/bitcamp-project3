package com.example.backend.entity.mongo;

import com.example.backend.dto.template.TemplateResponseDto;
import com.example.backend.entity.maria.enumData.DocType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Document(collection = "template")
@ToString
@Getter
public class Template<T> {

    
    @Id
    private String id;

    private Long writer;


    private DocType type;

    private List<Long> refList;

    @CreatedDate
    private LocalDate createDate;

    private List<Long> approverList;
    private List<Boolean> approverCheckList;

    private T typeData;

    @Builder
    public Template (Long writer, DocType type, List<Long> approverList, List<Long> refList, T typeData) {
        this.writer = writer;
        this.type = type;
        this.refList = refList;
        this.approverList = approverList;
        this.approverCheckList = new ArrayList<Boolean>();
        this.typeData = typeData;
    }

    public void updateCheckList(List<Boolean> update) {
        this.approverCheckList = update;
    }

    public TemplateResponseDto<T> toTemplateResponseDto(String writer, List<String> refList, List<String> approverList) {

        TemplateResponseDto<T> templateResponseDto = TemplateResponseDto.<T>builder()
                .writer(writer)
                .type(this.type)
                .refList(refList)
                .createDate(this.createDate)
                .approverList(approverList)
                .typeData(typeData).build();

        return templateResponseDto;
    }
}
