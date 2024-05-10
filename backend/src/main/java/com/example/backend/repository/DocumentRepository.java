package com.example.backend.repository;

import com.example.backend.entity.maria.Document;
import com.example.backend.repository.boardList.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String>, BoardList {


}
