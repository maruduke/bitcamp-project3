package com.example.backend.repository;

import com.example.backend.entity.maria.TaskProgress;
import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.repository.boardList.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskProgressRepository extends JpaRepository<TaskProgress, Long> {

    public Optional<TaskProgress> findByDocumentId(String documentId);

    public Optional<TaskProgress> deleteByDocumentId(String documentId);

    public Optional<TaskProgress> deleteAllByDocumentId(String documentId);

    public Optional<TaskProgress> findByRefUserIdAndState(Long ref_user_id, DocState docState);
}
