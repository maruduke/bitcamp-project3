package com.example.backend.repository;

import com.example.backend.entity.maria.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<User, Long> {
}
