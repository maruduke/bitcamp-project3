package com.example.backend.security;

import com.example.backend.entity.maria.User;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/* 인증처리를 위한 객체
*   사용자 정보를 조회*/
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        log.info(user.toString());
        return user;
    }
}
