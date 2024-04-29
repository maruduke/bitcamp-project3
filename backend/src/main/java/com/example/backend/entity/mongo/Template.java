package com.example.backend.entity.mongo;

import com.example.backend.entity.maria.enumData.DocType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

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



}
