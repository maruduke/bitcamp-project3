package com.example.backend.Jwt;

import com.example.backend.entity.maria.enumData.Authority;
import lombok.Getter;

@Getter
public class Subject {
    private final String email;
    private final Authority authority;
    private final String type;

    private Subject(String email, Authority authority, String type) {
        this.email = email;
        this.authority = authority;
        this.type = type;
    }

    public static Subject atk(String email, Authority authority) {
        return new Subject(email, authority, "ATK");
    }

    public static Subject rtk(String email, Authority authority) {
        return new Subject(email, authority, "RTK");
    }
}
