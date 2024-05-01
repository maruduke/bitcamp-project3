package com.example.backend.dto.user;

import com.example.backend.entity.maria.User;
import com.example.backend.entity.maria.enumData.Authority;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinDto {

    private String email;

    private String password;
    private String name;
    private String dept;
    private String position;
    private Authority authority;
    private String tel;

    public static JoinDto of(User user){
        return new JoinDto(
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getDept(),
                user.getPosition(),
                user.getAuthority(),
                user.getTel());
    }
}
