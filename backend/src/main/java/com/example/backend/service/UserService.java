package com.example.backend.service;

import com.example.backend.Jwt.JwtUtil;
import com.example.backend.dto.user.JoinDto;
import com.example.backend.dto.user.LoginDto;
import com.example.backend.dto.user.UserResponse;
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
    private final RedisService redisService;
    private final JwtUtil jwtUtil;

    public UserResponse Join(JoinDto joinDto) {
        boolean isExist = userRepository.existsByEmail(joinDto.getEmail());

        if(isExist) {
            log.info("중복된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(joinDto.getPassword());

        if(joinDto.getDept().equals("인사과")|| joinDto.getPosition().equals("CEO")){
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
        return UserResponse.of(user);
    }

    public UserResponse login(LoginDto loginDto) throws BadRequestException {
        User user = userRepository
                .findByEmail(loginDto.getEmail())
                .orElseThrow(()-> new BadRequestException("아이디가 존재하지않습니다."));

        boolean matches = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

        if(!matches) {
             throw new BadRequestException("비밀번호가 다릅니다.");
        }

        return UserResponse.of(user);
    }

    public void logout(String atk) {

        if(!jwtUtil.validateToken(atk)){
            redisService.deleteValues(atk);
            log.info("atk value 제거");
        }

        log.info(atk);



    }




}
