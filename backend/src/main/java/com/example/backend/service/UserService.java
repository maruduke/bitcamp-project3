package com.example.backend.service;

import com.example.backend.dto.user.JoinDto;
import com.example.backend.entity.maria.User;
import com.example.backend.entity.maria.enumData.Authority;
import com.example.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
                joinDto.getBirthDay(),
                joinDto.getTel());

        user = userRepository.save(user);
        log.info(user.toString());
        return JoinDto.of(user);
    }






    public User login(HttpServletRequest req){
        Optional<User> OptionalUser = userRepository.findByEmail(req.getParameter("email"));

        if(OptionalUser.isEmpty()){
            return null;
        }
        User user = OptionalUser.get();

        if(!user.getPassword().equals(req.getParameter("password"))){
            return null;
        }
        return user;
    }




}
