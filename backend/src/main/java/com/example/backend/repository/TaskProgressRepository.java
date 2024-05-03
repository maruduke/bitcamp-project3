package com.example.backend.repository;

import com.example.backend.entity.maria.TaskProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskProgressRepository extends JpaRepository<TaskProgress, Long> {

    public Optional<TaskProgress> findByDocumentId(String documentId);
}
