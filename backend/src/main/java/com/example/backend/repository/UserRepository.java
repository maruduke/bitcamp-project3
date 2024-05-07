package com.example.backend.repository;

import com.example.backend.entity.maria.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> getUserByEmail(String username);

    @Modifying
    @Query("UPDATE User u SET u.email = :email, u.tel = :tel WHERE u.name = :name")
    int updateUserByName(String email, String tel, String name);
}
