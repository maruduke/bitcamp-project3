package com.example.backend.entity.maria;

import com.example.backend.entity.maria.enumData.Authority;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String position;
    private String dept;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private LocalDate birthDay;

    @Column(nullable = false)
    private Authority authority;

    private Long supervisor;

    private String profile_image_path;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password,
                String dept, String position, Authority authority,
                LocalDate birthday, String tel) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dept = dept;
        this.position = position;
        this.authority = authority;
        this.birthDay = birthday;
        this.tel = tel;
    }

}
