package com.example.backend.entity.maria;

import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.entity.maria.enumData.DocType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;


@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document {

    @Id @Column(name="document_id")
    private String documentId;

    private Long writer;

    private String title;
    @Enumerated
    private DocType type;
    @Enumerated
    private DocState state;


    private LocalDate createDate;


    @Builder
    public Document(String documentId, Long writer, String title, DocType type, DocState state, LocalDate createDate) {
        this.documentId = documentId;
        this.writer = writer;
        this.title = title;
        this.type = type;
        this.state = state;
        this.createDate = createDate;
    }

    public void updateState(DocState state) {
        this.state = state;
    }
}
