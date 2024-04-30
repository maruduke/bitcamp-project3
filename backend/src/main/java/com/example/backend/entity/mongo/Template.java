package com.example.backend.entity.mongo;

import com.example.backend.entity.maria.enumData.DocType;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Document(collection = "template")
public class Template<T> {

    
    @Id
    private String id;

    private Long writer;
    private DocType dataType;

    private List<Long> refList;

    @CreatedDate
    private LocalDateTime createDate;

    private List<Long> approverList;
    private List<Boolean> approverCheckList;

    private T data;

    @Builder
    public Template (Long writer, DocType dataType,List<Long> refList, List<Long> approverList, T data) {
        this.writer = writer;
        this.dataType = dataType;
        this.refList = refList;
        this.approverList = new ArrayList<Long>();
        this.data = data;
    }

}
