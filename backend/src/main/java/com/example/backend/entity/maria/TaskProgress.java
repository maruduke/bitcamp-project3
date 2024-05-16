package com.example.backend.entity.maria;

import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.entity.maria.enumData.DocType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="progress_id")
    private Long refId;

    @Column(nullable = false)
    private String documentId;

    @Column(nullable = false)
    private DocState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_user_id", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(name = "ref_user_id")
    private Long refUserId;

    @Builder
    public TaskProgress(String documentId, DocState state, Long ref_user_id) {
        this.documentId = documentId;
        this.state = state;
        this.refUserId = ref_user_id;
    }

    @Override
    public String toString() {
        return "TaskProgress{" +
                "refId=" + refId +
                ", documentId='" + documentId + '\'' +
                ", state=" + state +
                ", ref_user_id=" + refUserId +
                '}';
    }
}
