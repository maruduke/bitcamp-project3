package com.example.backend.repository;

import com.example.backend.entity.maria.Ref;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefRepository extends JpaRepository<Ref, Long> {
}
