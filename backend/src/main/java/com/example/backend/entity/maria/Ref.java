package com.example.backend.entity.maria;

import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.entity.maria.enumData.DocType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(name="ref_user_id")
    private Long refUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ref_user_id", insertable = false, updatable = false)
    private User ref_user_id;

    @Builder
    public Ref(String documentId, DocState state, Long refUserId) {
        this.documentId = documentId;
        this.state = state;
        this.refUserId = refUserId;
    }
}
