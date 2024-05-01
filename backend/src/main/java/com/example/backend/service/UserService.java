package com.example.backend.service;

import com.example.backend.dto.user.JoinDto;
import com.example.backend.dto.user.LoginDto;
import com.example.backend.entity.maria.User;
import com.example.backend.entity.maria.enumData.Authority;
import com.example.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JoinDto Join(JoinDto joinDto) {
        boolean isExist = userRepository.existsByEmail(joinDto.getEmail());

        if(isExist) {
            log.info("중복된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(joinDto.getPassword());

        if(joinDto.getDept().equals("인사과")){
            joinDto.setAuthority(Authority.ADMIN);
        }else{
            joinDto.setAuthority(Authority.USER);
        }

        User user = new User(
                joinDto.getName(),
                joinDto.getEmail(),
                encodedPassword,
                joinDto.getDept(),
                joinDto.getPosition(),
                joinDto.getAuthority(),
                joinDto.getTel());

        user = userRepository.save(user);
        log.info(user.toString());
        return JoinDto.of(user);
    }

    public LoginDto login(LoginDto loginDto){
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());

        if(user.isEmpty()) {
            user.orElseThrow(()-> new RuntimeException("아이디가 존재하지 않습니다."));
        }else if(!passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
            user.orElseThrow(()-> new RuntimeException("비밀번호가 다릅니다."));
        }

        return LoginDto.of(user.get());
    }




}
