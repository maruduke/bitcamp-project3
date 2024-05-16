package com.example.backend.dto.template;

import com.example.backend.entity.maria.enumData.DocType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class TemplateResponseDto<T> {

    private String writer;
    private DocType type;

    private List<String> refList;
    private List<String> approverList;

    private LocalDate createDate;
    private T typeData;

}
