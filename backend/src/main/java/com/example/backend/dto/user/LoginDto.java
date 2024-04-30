package com.example.backend.dto.user;

import com.example.backend.entity.maria.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String email;
    private String password;

    public static LoginDto of(User user){
        return new LoginDto(
                user.getEmail(),
                user.getPassword());
    }
}
