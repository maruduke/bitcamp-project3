package com.example.backend.repository;

import com.example.backend.entity.maria.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
