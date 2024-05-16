package com.example.backend.dto.user;

import com.example.backend.entity.maria.User;
import com.example.backend.entity.maria.enumData.Authority;
import lombok.Getter;

@Getter
public class UserResponse {
    private final String email;
    private final String name;
    private final Authority authority;

    public UserResponse(String email, String name, Authority authority) {
        this.email = email;
        this.name = name;
        this.authority = authority;
    }

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getEmail(),
                user.getName(),
                user.getAuthority());
    }
}
