package com.example.backend.entity.maria;

import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.entity.maria.enumData.DocType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ref {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ref_id")
    private Long refId;

    @Column(nullable = false, name="document_id")
    private String documentId;

    @Column(nullable = false)
    private DocState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ref_user_id")
    private User ref_user_id;

}
