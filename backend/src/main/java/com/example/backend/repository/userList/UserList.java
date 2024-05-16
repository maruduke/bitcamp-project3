package com.example.backend.repository.userList;

import com.example.backend.entity.maria.User;

import java.util.List;
import java.util.Optional;

public interface UserList {

    Optional<List<User>> findAllByEmailOrderByField(List<String> emails);
}
