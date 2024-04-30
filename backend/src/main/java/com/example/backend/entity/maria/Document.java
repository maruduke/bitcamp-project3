package com.example.backend.entity.maria;

import com.example.backend.dto.template.AccountDto;
import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.entity.maria.enumData.DocType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
public class Document {

    @Id @Column(name="document_id")
    private String documentId;

    private String writer;

    private String title;
    private DocType type;

    public static Document dtoToEntity(AccountDto accountDto){
        return Document.builder()
                .documentId(accountDto.getDocumentId())
                .writer(accountDto.getWriter())
                .title(accountDto.getTitle())
                .type(accountDto.getType())
                .build();
    }

}
