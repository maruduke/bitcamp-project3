package com.example.backend.dto.template;

import com.example.backend.entity.maria.enumData.DocType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TemporaryResponseDto<T> {
    private DocType type;
    private T typeData;
}
