package com.example.backend.dto.template;

import com.example.backend.entity.maria.Document;
import com.example.backend.entity.maria.enumData.DocType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

//    mariaDB
    private String documentId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String writer;

    @NotEmpty
    private DocType type;

    public static AccountDto entityToDto(Document document){
        AccountDto accountDto = AccountDto.builder()
                .documentId(document.getDocumentId())
                .title(document.getTitle())
                .writer(document.getWriter())
                .type(document.getType())
                .build();

        return accountDto;
    }

//    mongoDB
}
