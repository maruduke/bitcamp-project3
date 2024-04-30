package com.example.backend.dto.user;

import com.example.backend.entity.maria.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@ToString
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
