package com.example.backend.entity.maria;

import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.entity.maria.enumData.DocType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Document {

    @Id @Column(name="document_id")
    private String documentId;

    private String writer;

    private String title;
    private DocType type;
    private DocState state;

    @CreatedDate
    private LocalDate createDate;


}
