package com.example.backend.entity.maria;

import com.example.backend.entity.maria.enumData.DocType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Document {

    @Id @Column(name="document_id")
    private String documentId;

    private String writer;

    private String title;
    private DocType type;
}
