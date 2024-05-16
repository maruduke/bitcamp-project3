package com.example.backend.repository;

import com.example.backend.dto.user.HeaderDto;
import com.example.backend.entity.maria.User;
import com.example.backend.repository.userList.UserList;
import io.jsonwebtoken.Header;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserList {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<List<User>> findAllByEmailIn(List<String> refList);

    @Query("SELECT u.name FROM User u WHERE u.email = :email")
    String findNameByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.tel = :tel WHERE u.email = :email")
    int updateUserByEmail(String tel, String email);

}
