package com.example.backend;

import com.example.backend.entity.maria.User;
import com.example.backend.entity.maria.enumData.Authority;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

//    @Test
//    public void testInsertUser(){
//        User user = new User().builder()
//                .name("서주원")
//                .email("sgw0816@naver.com")
//                .password("1234")
//                .dept("인사과")
//                .position("대리")
//                .birthDay(LocalDate.of(1996, 8, 16))
//                .authority(Authority.ADMIN)
//                .tel("010-5456-4973")
//                .build();
//
//        userRepository.save(user);
//    }

}
