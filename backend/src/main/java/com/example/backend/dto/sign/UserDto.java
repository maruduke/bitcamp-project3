package com.example.backend.dto.sign;

import com.example.backend.entity.maria.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class UserDto {
    String name;
    String email;
    String position;

    public UserDto (User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.position = user.getPosition();

    }
}
