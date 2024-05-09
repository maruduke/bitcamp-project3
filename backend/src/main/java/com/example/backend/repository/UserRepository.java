package com.example.backend.repository;

import com.example.backend.entity.maria.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> getUserByEmail(String username);

    Optional<List<User>> findAllByEmailIn(List<String> refList);

    @Modifying
    @Query("UPDATE User u SET u.tel = :tel WHERE u.email = :email")
    int updateUserByEmail(String tel, String email);

}
